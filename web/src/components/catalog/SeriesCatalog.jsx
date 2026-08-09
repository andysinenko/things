import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {fetchSeries} from "../books/reducer/SeriesSlice";
import "./Catalog.css"

const SeriesCatalog = () => {
    const { series, loading, error } = useSelector(state => state.seriesReducer);
    const dispatch = useDispatch();

    const handleAddSeries = (series) => {

    }

    const handleEditSeries = (series) => {

    }

    const handleDelSeries = (series) => {

    }

    useEffect(() => {
        dispatch(fetchSeries());
    }, [dispatch]);

    return (
        <div>
            <nav className="cat-buttons-toolbar" aria-label="Series toolbar">
                <div className="toolbar-divider" aria-hidden="true" />
                <button className="thbtn-add" onClick={handleAddSeries}>
                    + Add series
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
                    {series && series.length > 0 ? series.map((s) => (
                        <tr key={s.id}>
                            <td style={{ color: "#9ca3af" }}>{s.id}</td>
                            <td style={{ fontWeight: 500, width:"35%" }}>{s.name}</td>
                            <td style={{ color: "#9ca3af", width:"35%" }}>{s.note}</td>
                            <td>
                                <div style={{ display: "flex", gap: 4, justifyContent: "center" }}>
                                    <button
                                        className="table-action-btn edit-btn"
                                        title="Edit"
                                        onClick={() => handleEditSeries(s)}
                                        aria-label="Edit series">
                                        ✎
                                    </button>
                                    <button
                                        className="table-action-btn delete-btn"
                                        title="Delete"
                                        onClick={() => handleDelSeries(s)}
                                        aria-label="Delete series">
                                        ✕
                                    </button>
                                </div>
                            </td>
                        </tr>
                    )) : (
                        <tr>
                            <td colSpan="10" style={{ textAlign: "center", padding: "32px 0", color: "#9ca3af" }}>
                                No series found
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </section>
        </div>
    );
}

export default SeriesCatalog;