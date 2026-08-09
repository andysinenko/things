import {Paginator} from "../layout/pagination/Paginator";
import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {fetchGenres} from "../books/reducer/GenresSlice";
import {fetchAuthors} from "../books/reducer/AuthorsSlice";
import "./Catalog.css"

const AuthorsCatalog = () => {
    const { authors, loading, error } = useSelector(state => state.authorsReducer);
    const total      = useSelector(state => state.booksReducer.total);
    const pageNumber = useSelector(state => state.booksReducer.pageNumber);
    const pageSize   = 15;
    const dispatch = useDispatch();

    const handleAddAuthor = (author) => {

    }

    const handleEditAuthor = (author) => {

    }

    const handleDelAuthor = (author) => {

    }

    const onChangePage = (pageNumber, pageSize) => {

    }

    useEffect(() => {
        dispatch(fetchGenres());
        dispatch(fetchAuthors());
    }, [dispatch]);

    return (
        <div>
            <nav className="cat-buttons-toolbar" aria-label="Authors toolbar">
                <div className="toolbar-divider" aria-hidden="true" />
                <button className="thbtn-add" onClick={handleAddAuthor}>
                    + Add author
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
                        <th style={{ cursor: "pointer", width:"20%" }}>Genre</th>
                        <th style={{ textAlign: "center" }}>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {authors && authors.length > 0 ? authors.map((author) => (
                        <tr key={author.id}>
                            <td style={{ color: "#9ca3af" }}>{author.id}</td>
                            <td style={{ fontWeight: 500, width:"35%" }}>{author.name}</td>
                            <td style={{ color: "#9ca3af", width:"35%" }}>{author.note}</td>
                            <td d="true" style={{color: "#6b7280", width:"20%" }}>{author.genre.name}</td>
                            <td>
                                <div style={{ display: "flex", gap: 4, justifyContent: "center" }}>
                                    <button
                                        className="table-action-btn edit-btn"
                                        title="Edit"
                                        onClick={() => handleEditAuthor(author)}
                                        aria-label="Edit author">
                                        ✎
                                    </button>
                                    <button
                                        className="table-action-btn delete-btn"
                                        title="Delete"
                                        onClick={() => handleDelAuthor(author)}
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

                <Paginator
                    pageNumber={pageNumber}
                    totalPages={total}
                    pageSize={pageSize}
                    onChangePage={onChangePage}
                />
            </section>
        </div>
    );
}

export default AuthorsCatalog;