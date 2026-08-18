import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {fetchGenres} from "../books/reducer/GenresSlice";
import {addAuthor, fetchAuthors, updateAuthor, deleteAuthor} from "../books/reducer/AuthorsSlice";
import "./Catalog.css"

const AuthorsCatalog = () => {
    const { authors, loading, error } = useSelector(state => state.authorsReducer);
    const { genres } = useSelector(state => state.genresReducer);
    const dispatch = useDispatch();

    const [name, setName] = useState("");
    const [note, setNote] = useState("");
    const [genre, setGenre] = useState(0);

    const [editingId, setEditingId] = useState(null);
    const [editName, setEditName] = useState("");
    const [editNote, setEditNote] = useState("");
    const [editGenre, setEditGenre] = useState("");

    const handleAddAuthor = async (e) => {
        e.preventDefault();
        try {
            if (!name) {
                console.error("name is required");
            } else {
                const author = {name: name, note: note, genre_id: genre};
                dispatch(addAuthor(author));
                setGenre("");
                setNote("");
                setName("");
            }
        } catch (err) {
            console.error("Error:", err);
        }
    }

    const startEdit = (author) => {
        setEditingId(author.id);
        setEditName(author.name || "");
        setEditNote(author.note || "");
        setEditGenre(author.genre?.id || "");
    };

    const cancelEdit = () => {
        setEditingId(null);
    };

    const handleUpdateAuthor = (authorId) => {
        if (!editName) {
            console.error("name is required");
            return;
        }
        const author={
            name: editName,
            note: editNote,
            genre_id: editGenre ? Number(editGenre) : null};

        dispatch(updateAuthor({
            id: authorId, author: author
        }));

        setEditingId(null);
    };

    const handleDelAuthor = (author) => {
        dispatch(deleteAuthor(author.id));
    }

    useEffect(() => {
        dispatch(fetchGenres());
        dispatch(fetchAuthors());
    }, [dispatch]);

    return (
        <div>
            <nav className="cat-buttons-toolbar" aria-label="Authors toolbar">
                <div className="modal-field">
                    <input
                        className="modal-input"
                        type="text"
                        name="name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        placeholder="Author name"
                        maxLength="100"
                    />
                </div>

                <div className="modal-field">
                    <input
                        className="modal-input"
                        type="text"
                        name="note"
                        value={note}
                        onChange={(e) => setNote(e.target.value)}
                        placeholder="Note"
                        maxLength="100"
                    />
                </div>

                <div className="modal-field">
                    <select
                        style={{padding: "7px"}}
                        value={genre || ""}
                        onChange={(e) => setGenre(Number(e.target.value))}>
                        <option value="" disabled hidden>Select genre</option>
                        {genres.map((g) => (
                            <option key={g.id} value={g.id}>{g.name}</option>
                        ))}
                    </select>
                </div>
                <button className="thbtn-add" onClick={handleAddAuthor} disabled={!name.trim()}>
                    + Add author
                </button>
            </nav>

            <section className="tableContainer">
                <table className="table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th style={{width:"35%"}}>Name</th>
                        <th style={{width:"35%"}}>Note</th>
                        <th style={{width:"20%"}}>Genre</th>
                        <th style={{textAlign: "center"}}>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {authors && authors.length > 0 ? authors.map((author) => {
                        const isEditing = editingId === author.id;
                        return (
                            <tr key={author.id}>
                                <td style={{color: "#9ca3af"}}>{author.id}</td>

                                <td style={{width:"35%"}}>
                                    {isEditing ? (
                                        <input
                                            className={`modal-input ${!editName ? "input-error" : ""}`}
                                            value={editName}
                                            onChange={(e) => setEditName(e.target.value)}
                                            autoFocus
                                        />
                                    ) : (
                                        author.name
                                    )}
                                </td>

                                <td style={{width:"35%"}}>
                                    {isEditing ? (
                                        <input
                                            className="modal-input"
                                            value={editNote}
                                            onChange={(e) => setEditNote(e.target.value)}
                                        />
                                    ) : (
                                        author.note
                                    )}
                                </td>

                                <td style={{width:"20%"}}>
                                    {isEditing ? (
                                        <select
                                            value={editGenre}
                                            onChange={(e) => setEditGenre(e.target.value)}
                                        >
                                            <option value="">—</option>
                                            {genres.map((g) => (
                                                <option key={g.id} value={g.id}>{g.name}</option>
                                            ))}
                                        </select>
                                    ) : (
                                        author.genre?.name || '—'
                                    )}
                                </td>

                                <td>
                                    <div style={{display: "flex", gap: 4, justifyContent: "center"}}>
                                        {isEditing ? (
                                            <>
                                                <button
                                                    className="table-action-btn save-btn"
                                                    title="Save"
                                                    onClick={() => handleUpdateAuthor(author.id)}
                                                >
                                                    ✓
                                                </button>
                                                <button
                                                    className="table-action-btn cancel-btn"
                                                    title="Cancel"
                                                    onClick={cancelEdit}
                                                >
                                                    ✕
                                                </button>
                                            </>
                                        ) : (
                                            <>
                                                <button
                                                    className="table-action-btn edit-btn"
                                                    title="Edit"
                                                    onClick={() => startEdit(author)}
                                                >
                                                    ✎
                                                </button>
                                                <button
                                                    className="table-action-btn delete-btn"
                                                    title="Delete"
                                                    onClick={() => handleDelAuthor(author)}
                                                >
                                                    ✕
                                                </button>
                                            </>
                                        )}
                                    </div>
                                </td>
                            </tr>
                        );
                    }) : (
                        <tr>
                            <td colSpan="5" style={{textAlign: "center", padding: "32px 0", color: "#9ca3af"}}>
                                No authors found
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </section>
        </div>
    );
}

export default AuthorsCatalog;