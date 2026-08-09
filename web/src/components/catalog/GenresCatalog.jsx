import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {fetchGenres} from "../books/reducer/GenresSlice";
import "./Catalog.css"

const GenresCatalog = () => {
    const { genres, loading, error } = useSelector(state => state.genresReducer);
    const dispatch = useDispatch();

    const handleAddGenre = (genre) => {

    }

    const handleEditGenre = (genre) => {

    }

    const handleDelGenre = (genre) => {

    }

    useEffect(() => {
        dispatch(fetchGenres());
    }, [dispatch]);

    return (
        <div>
            <nav className="cat-buttons-toolbar" aria-label="Genres toolbar">
                <div className="toolbar-divider" aria-hidden="true" />
                <button className="thbtn-add" onClick={handleAddGenre}>
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
                    {genres && genres.length > 0 ? genres.map((genre) => (
                        <tr key={genre.id}>
                            <td style={{ color: "#9ca3af" }}>{genre.id}</td>
                            <td style={{ fontWeight: 500, width:"35%" }}>{genre.name}</td>
                            <td style={{ color: "#9ca3af", width:"35%" }}>{genre.note}</td>
                            <td>
                                <div style={{ display: "flex", gap: 4, justifyContent: "center" }}>
                                    <button
                                        className="table-action-btn edit-btn"
                                        title="Edit"
                                        onClick={() => handleEditGenre(genre)}
                                        aria-label="Edit author">
                                        ✎
                                    </button>
                                    <button
                                        className="table-action-btn delete-btn"
                                        title="Delete"
                                        onClick={() => handleDelGenre(genre)}
                                        aria-label="Delete author">
                                        ✕
                                    </button>
                                </div>
                            </td>
                        </tr>
                    )) : (
                        <tr>
                            <td colSpan="10" style={{ textAlign: "center", padding: "32px 0", color: "#9ca3af" }}>
                                No books found
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