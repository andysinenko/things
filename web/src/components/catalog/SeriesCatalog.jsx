import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import "./Catalog.css"
import {addSeries, deleteSeries, fetchSeries, updateSeries} from "../books/reducer/SeriesSlice";

const SeriesCatalog = () => {
    const { series, loading, error } = useSelector(state => state.seriesReducer);
    const dispatch = useDispatch();

    const [name, setName] = useState("");
    const [note, setNote] = useState("");

    const [editingId, setEditingId] = useState(null);
    const [editName, setEditName] = useState("");
    const [editNote, setEditNote] = useState("");

    const handleAddSeries = async (e) => {
        e.preventDefault();
        try {
            if (!name) {
                console.error("name is required");
            } else {
                const series = {name: name, note: note};
                dispatch(addSeries(series));
                setNote("");
                setName("");
            }
        } catch (err) {
            console.error("Error:", err);
        }
    }

    const startEdit = (series) => {
        setEditingId(series.id);
        setEditName(series.name || "");
        setEditNote(series.note || "");
    };

    const cancelEdit = () => {
        setEditingId(null);
    };

    const update = (seriesId) => {
        if (!editName) {
            console.error("name is required");
            return;
        }

        const series = {
            name: editName,
            note: editNote
        };

        dispatch(updateSeries({
            id: seriesId, series: series
        }));

        setEditingId(null);
    };

    const handleDelSeries = (series) => {
        dispatch(deleteSeries(series.id));
    }

    useEffect(() => {
        dispatch(fetchSeries());
    }, [dispatch]);

    return (
        <div>
            <nav className="cat-buttons-toolbar" aria-label="Series toolbar">
                <div className="modal-field">
                    <input
                        className="modal-input"
                        type="text"
                        name="name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        placeholder="Series name"
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
                <button className="thbtn-add" onClick={handleAddSeries} disabled={!name.trim()}>
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
                    {series && series.length > 0 ? series.map((ser) => {
                        const isEditing = editingId === ser.id;
                        return (
                            <tr key={ser.id}>
                                <td style={{width: "5%", color: "#9ca3af"}}>{ser.id}</td>

                                <td style={{width:"35%"}}>
                                    {isEditing ? (
                                        <input
                                            className={`modal-input ${!editName ? "input-error" : ""}`}
                                            value={editName}
                                            onChange={(e) => setEditName(e.target.value)}
                                            autoFocus
                                        />
                                    ) : ( ser.name )}
                                </td>

                                <td style={{width:"35%"}}>
                                    {isEditing ? (
                                        <input
                                            className="modal-input"
                                            value={editNote}
                                            onChange={(e) => setEditNote(e.target.value)}
                                        />
                                    ) : ( ser.note )}
                                </td>

                                <td>
                                    <div style={{display: "flex", gap: 4, justifyContent: "center"}}>
                                        {isEditing ? (
                                            <>
                                                <button
                                                    className="table-action-btn save-btn"
                                                    title="Save"
                                                    onClick={() => update(ser.id)}>
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
                                                    onClick={() => startEdit(ser)}>
                                                    ✎
                                                </button>
                                                <button
                                                    className="table-action-btn delete-btn"
                                                    title="Delete"
                                                    onClick={() => handleDelSeries(ser)}>
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