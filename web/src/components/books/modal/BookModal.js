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
        if (!place) return '';
        const names = [];
        let current = place;
        while (current) {
            names.unshift(current.name);
            current = current.parent;
        }
        return names.join(' -> ');
    };

    if (!isOpen) return null;

    const renderEditAdd = () => (
        <>
            <div className="th-modal-header">
                <h5>{modalType === "add" ? "Add book" : "Edit book"}</h5>
            </div>

            <div className="modal-body">
                <input
                    placeholder="id"
                    className="th-main-input"
                    name="id"
                    value={selectedBook.id}
                    readOnly
                    disabled
                />
                <input
                    className="th-main-input"
                    type="text"
                    name="title"
                    value={selectedBook.title}
                    onChange={handleChange}
                    placeholder="Enter title of book"
                    maxLength="512"
                />
                <input
                    className="th-main-input"
                    type="text"
                    name="volume"
                    value={selectedBook.volume}
                    onChange={handleChange}
                    placeholder="Enter volume of book"
                    maxLength="512"
                />

                <select
                    aria-label="Genre name"
                    value={selectedBook.genre?.id || ""}
                    onChange={(e) => {
                        const selectedGenre = genres.find(g => g.id === Number(e.target.value));
                        setSelectedBook((prev) => ({ ...prev, genre: selectedGenre }));
                    }}
                >
                    <option value="" disabled hidden>Genre name</option>
                    {genres.map((genre) => (
                        <option key={genre.id} value={genre.id}>{genre.name}</option>
                    ))}
                </select>

                <select
                    multiple
                    aria-label="Authors"
                    value={selectedBook.authors?.map(a => a.id) || []}
                    onChange={(e) => {
                        const selectedIds = Array.from(e.target.selectedOptions, o => Number(o.value));
                        const selectedAuthors = authors.filter(a => selectedIds.includes(a.id));
                        setSelectedBook((prev) => ({ ...prev, authors: selectedAuthors }));
                    }}
                >
                    {authors.map((author) => (
                        <option key={author.id} value={author.id}>{author.name}</option>
                    ))}
                </select>

                <select
                    aria-label="Series"
                    value={selectedBook.series?.id || ""}
                    onChange={(e) => {
                        const selectedSeries = series.find(s => s.id === Number(e.target.value));
                        setSelectedBook((prev) => ({ ...prev, series: selectedSeries }));
                    }}
                >
                    <option value="" disabled hidden>Series</option>
                    {series.map((s) => (
                        <option key={s.id} value={s.id}>{s.name}</option>
                    ))}
                </select>

                <button type="button" className="th-main-button" onClick={onPlacesOpenDialogBox}>
                    {selectedBook.place
                        ? `Place: ${getFullPlacePath(selectedBook.place)} ✅`
                        : "Select place ❓"}
                </button>

                <input
                    className="th-main-input"
                    type="text"
                    name="year"
                    value={selectedBook.year?.substring(0, 4) || ""}
                    onChange={handleChange}
                    placeholder="Enter year of release"
                />
                <input
                    className="th-main-input"
                    type="text"
                    name="description"
                    value={selectedBook.description}
                    onChange={handleChange}
                    placeholder="Description"
                    maxLength="512"
                />
            </div>

            <div className="modal-footer">
                <button className="th-main-button" onClick={onClose}>Close</button>
                <button className="th-main-button" onClick={onSubmit}>Save</button>
            </div>
        </>
    );

    const renderDelete = () => (
        <>
            <div className="th-modal-header">
                <h5>Delete book</h5>
            </div>
            <div className="modal-body">
                <span>Are you sure you want to delete "{selectedBook?.title || "this book"}"?</span>
            </div>
            <div className="modal-footer">
                <button className="th-main-button" onClick={onClose}>Close</button>
                <button className="th-main-button" onClick={onSubmit}>Delete</button>
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