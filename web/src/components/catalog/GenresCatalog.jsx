import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {addGenre, deleteGenre, fetchGenres, updateGenre} from "../books/reducer/GenresSlice";
import "./Catalog.css"


const GenresCatalog = () => {
    const { genres, loading, error } = useSelector(state => state.genresReducer);
    const dispatch = useDispatch();
    const [name, setName] = useState("");
    const [note, setNote] = useState("");

    const [editingId, setEditingId] = useState(null);
    const [editName, setEditName] = useState("");
    const [editNote, setEditNote] = useState("");

    const handleAddGenre = async (e) => {
        e.preventDefault();
        try {
            if (!name) {
                console.error("name is required");
            } else {
                const genre = {name: name, note: note};
                dispatch(addGenre(genre));
                setNote("");
                setName("");
            }
        } catch (err) {
            console.error("Error:", err);
        }
    }

    const startEdit = (genre) => {
        setEditingId(genre.id);
        setEditName(genre.name || "");
        setEditNote(genre.note || "");
    };

    const cancelEdit = () => {
        setEditingId(null);
    };

    const update = (genreId) => {
        if (!editName) {
            console.error("name is required");
            return;
        }

        const genre = {
            name: editName,
            note: editNote
        };

        dispatch(updateGenre({
            id: genreId, genre: genre
        }));

        setEditingId(null);
    };

    const handleDelGenre = (genre) => {
        dispatch(deleteGenre(genre.id));
    }

    useEffect(() => {
        dispatch(fetchGenres());
    }, [dispatch]);

    return (
        <div>
            <nav className="cat-buttons-toolbar" aria-label="Genres toolbar">
                <div className="modal-field">
                    <input
                        className="modal-input"
                        type="text"
                        name="name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        placeholder="Genre name"
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
                <button className="thbtn-add" onClick={handleAddGenre} disabled={!name.trim()}>
                    + Add genre
                </button>
            </nav>

            {/* ── Table ── */}
            <section className="tableContainer">
                <table className="table">
                    <thead>
                    <tr>
                        <th style={{ cursor: "pointer" }}>ID</th>
                        <th style={{ cursor: "pointer", width:"35%" }}>Name</th>
                        <th style={{ color: "#9ca3af", width:"35%" }}>Note</th>
                        <th style={{ textAlign: "center" }}>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {genres && genres.length > 0 ? genres.map((genre) => {
                        const isEditing = editingId === genre.id;
                        return (
                            <tr key={genre.id}>
                                <td style={{width: "5%", color: "#9ca3af"}}>{genre.id}</td>

                                <td style={{width:"35%"}}>
                                    {isEditing ? (
                                        <input
                                            className={`modal-input ${!editName ? "input-error" : ""}`}
                                            value={editName}
                                            onChange={(e) => setEditName(e.target.value)}
                                            autoFocus
                                        />
                                    ) : ( genre.name )}
                                </td>

                                <td style={{width:"35%"}}>
                                    {isEditing ? (
                                        <input
                                            className="modal-input"
                                            value={editNote}
                                            onChange={(e) => setEditNote(e.target.value)}
                                        />
                                    ) : ( genre.note )}
                                </td>

                                <td>
                                    <div style={{display: "flex", gap: 4, justifyContent: "center"}}>
                                        {isEditing ? (
                                            <>
                                                <button
                                                    className="table-action-btn save-btn"
                                                    title="Save"
                                                    onClick={() => update(genre.id)}>
                                                    ✓
                                                </button>
                                                <button
                                                    className="table-action-btn cancel-btn"
                                                    title="Cancel"
                                                    onClick={cancelEdit}>
                                                    ✕
                                                </button>
                                            </>
                                        ) : (
                                            <>
                                                <button
                                                    className="table-action-btn edit-btn"
                                                    title="Edit"
                                                    onClick={() => startEdit(genre)}>
                                                    ✎
                                                </button>
                                                <button
                                                    className="table-action-btn delete-btn"
                                                    title="Delete"
                                                    onClick={() => handleDelGenre(genre)}>
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
                                No genres found
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </section>
        </div>
    );
}

export default GenresCatalog;