import React, { useState } from "react";
import PlaceModal from "../../places/modal/PlaceModal";

const BookModal = ({
                       isOpen,
                       onClose,
                       onSubmit,
                       modalType,
                       selectedBook,
                       setSelectedBook,
                       genres,
                       series,
                       authors = [],
                       places,
                   }) => {
    const [isTreeModalOpen, setIsTreeModalOpen] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setSelectedBook((prev) => ({ ...prev, [name]: value }));
    };

    const onNodeSelect = (nodePlace) => {
        setSelectedBook((prev) => ({ ...prev, place: nodePlace }));
        setIsTreeModalOpen(false);
    };

    const onPlacesOpenDialogBox = (e) => {
        e.preventDefault();
        setIsTreeModalOpen(true);
    };

    const getFullPlacePath = (place) => {
        if (!place) return "";
        const names = [];
        let current = place;
        while (current) {
            names.unshift(current.name);
            current = current.parent;
        }
        return names.join(" → ");
    };

    if (!isOpen) return null;

    const renderEditAdd = () => (
        <>
            <div className="th-modal-header">
                <span className="th-modal-title">
                    {modalType === "add" ? "Add book" : "Edit book"}
                </span>
                <button className="th-modal-close-btn" onClick={onClose} aria-label="Close">
                    ✖️
                </button>
            </div>

            <div className="modal-body">
                {/* Title + Volume */}
                <div className="modal-field-row">
                    <div className="modal-field">
                        <label>Title</label>
                        <input
                            className="modal-input"
                            type="text"
                            name="title"
                            value={selectedBook.title}
                            onChange={handleChange}
                            placeholder="Book title"
                            maxLength="512"
                        />
                    </div>
                    <div className="modal-field">
                        <label>Volume</label>
                        <input
                            className="modal-input"
                            type="text"
                            name="volume"
                            value={selectedBook.volume}
                            onChange={handleChange}
                            placeholder="1"
                            maxLength="512"
                        />
                    </div>
                </div>

                {/* Author + Genre */}
                <div className="modal-field-row">
                    <div className="modal-field">
                        <label>Author</label>
                        <select
                            multiple
                            aria-label="Authors"
                            value={selectedBook.authors?.map((a) => a.id) || []}
                            onChange={(e) => {
                                const selectedIds = Array.from(e.target.selectedOptions, (o) => Number(o.id));
                                setSelectedBook((prev) => ({ ...prev, authors: selectedIds }));
                            }}
                        >
                            {authors.map((author) => (
                                <option key={author.id} value={author.id}>{author.name}</option>
                            ))}
                        </select>
                    </div>
                    <div className="modal-field">
                        <label>Genre</label>
                        <select
                            aria-label="Genre name"
                            value={selectedBook.genre?.id || ""}
                            onChange={(e) => {
                                setSelectedBook((prev) => ({ ...prev, genre: e.target.value }));
                            }}
                        >
                            <option value="" disabled hidden>Select genre</option>
                            {genres.map((genre) => (
                                <option key={genre.id} value={genre.id}>{genre.name}</option>
                            ))}
                        </select>
                    </div>
                </div>

                {/* Series + Year */}
                <div className="modal-field-row">
                    <div className="modal-field">
                        <label>Series</label>
                        <select
                            aria-label="Series"
                            value={selectedBook.series?.id || ""}
                            onChange={(e) => {
                                setSelectedBook((prev) => ({ ...prev, series: e.target.value }));
                            }}
                        >
                            <option value="" disabled hidden>Select series</option>
                            {series.map((s) => (
                                <option key={s.id} value={s.id}>{s.name}</option>
                            ))}
                        </select>
                    </div>
                    <div className="modal-field">
                        <label>Year</label>
                        <input
                            className="modal-input"
                            type="text"
                            name="year"
                            value={selectedBook.year?.substring(0, 4) || ""}
                            onChange={handleChange}
                            placeholder="2024"
                        />
                    </div>
                </div>

                {/* Place */}
                <div className="modal-field">
                    <label>Place</label>
                    <button
                        type="button"
                        className="modal-place-btn"
                        onClick={onPlacesOpenDialogBox}
                    >
                        <span>
                            {selectedBook.place
                                ? getFullPlacePath(selectedBook.place)
                                : "Select place"}
                        </span>
                        <span style={{ fontSize: 13, opacity: 0.5 }}>
                            {selectedBook.place ? "✓" : "›"}
                        </span>
                    </button>
                </div>

                {/* Description */}
                <div className="modal-field">
                    <label>Description</label>
                    <input
                        className="modal-input"
                        type="text"
                        name="description"
                        value={selectedBook.description}
                        onChange={handleChange}
                        placeholder="Optional notes"
                        maxLength="512"
                    />
                </div>
            </div>

            <div className="modal-footer">
                <button className="modal-button" onClick={onClose}>Cancel</button>
                <button className="modal-button modal-btn-primary" onClick={onSubmit}>Save</button>
            </div>
        </>
    );

    const renderDelete = () => (
        <>
            <div className="th-modal-header">
                <span className="th-modal-title">Delete book</span>
                <button className="th-modal-close-btn" onClick={onClose} aria-label="Close">
                    ✕
                </button>
            </div>
            <div className="modal-body">
                <p style={{ color: "#4b5563", fontSize: 14, lineHeight: 1.5 }}>
                    Are you sure you want to delete{" "}
                    <strong style={{ color: "#1a2332" }}>"{selectedBook?.title || "this book"}"</strong>?
                    This action cannot be undone.
                </p>
            </div>
            <div className="modal-footer">
                <button className="modal-button" onClick={onClose}>Cancel</button>
                <button
                    className="modal-button"
                    onClick={onSubmit}
                    style={{
                        background: "#b91c1c",
                        color: "#fff",
                        border: "1px solid #b91c1c",
                    }}
                >
                    Delete
                </button>
            </div>
        </>
    );

    return (
        <>
            <div className="th-modal-overlay">
                <div className="th-modal-content">
                    {(modalType === "add" || modalType === "edit") && renderEditAdd()}
                    {modalType === "delete" && renderDelete()}
                </div>
            </div>

            <PlaceModal
                places={places}
                onCrossClick={onNodeSelect}
                isOpen={isTreeModalOpen}
                onClose={() => setIsTreeModalOpen(false)}
                onSubmit={() => setIsTreeModalOpen(false)}
                onAddChild={null}
            />
        </>
    );
};

export default BookModal;