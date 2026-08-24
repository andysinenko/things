import React from "react";

const PdfBookModal = ({selectedPdfBook, setSelectedPdfBook, isOpen, onOpen, onClose, categories, authors, onSubmit}) => {
    const handleChange = e => {
        const {name, value} = e.target;
        setSelectedPdfBook(prev => ({ ...prev, [name]: value }));
    }

    if (!isOpen) return null;

    return (
        <>
            <div className="th-modal-overlay">
                <div className="th-modal-content">
                    <div className="th-modal-header">
                        <span className="th-modal-title">Edit pdf book info</span>
                        <button className="th-modal-close-btn" onClick={onClose} aria-label="Close">✖️</button>
                    </div>
                    <div className="modal-body">
                        <div className="modal-field">
                            <input
                                className="modal-input"
                                type="text"
                                name="title"
                                value={selectedPdfBook.title}
                                onChange={handleChange}
                                placeholder="Book title"
                                maxLength="256"
                            />
                        </div>
                        <div className="modal-field-row">
                            <div className="modal-field">
                                <select
                                    name="category"
                                    aria-label="Category"
                                    value={selectedPdfBook.category?.id || selectedPdfBook.category || ''}
                                    onChange={(e) => setSelectedPdfBook(prev => ({
                                        ...prev,
                                        category: Number(e.target.value)
                                    }))}
                                    className="th-main-input"
                                >
                                    <option value="" disabled hidden>Category</option>
                                    {categories?.map(cat => (
                                        <option key={cat.id} value={cat.id}>{cat.name}</option>
                                    ))}
                                </select>
                            </div>
                            <div className="modal-field">
                                <select
                                    name="author"
                                    aria-label="Author"
                                    value={selectedPdfBook.author?.id || selectedPdfBook.author || ''}
                                    onChange={(e) => setSelectedPdfBook(prev => ({
                                        ...prev,
                                        author: Number(e.target.value)
                                    }))}
                                    className="th-main-input"
                                >
                                    <option value="" disabled hidden>Author</option>
                                    {authors?.map(author => (
                                        <option key={author.id} value={author.id}>{author.name}</option>
                                    ))}
                                </select>
                            </div>
                        </div>
                        {/* Language + Year */}
                        <div className="modal-field-row">
                            <div className="modal-field">
                                <select
                                    name="language"
                                    value={selectedPdfBook.language}
                                    onChange={handleChange}
                                    className="th-main-input"
                                >
                                    <option value="" disabled hidden>Language</option>
                                    <option value="EN">EN</option>
                                    <option value="RU">RU</option>
                                    <option value="UA">UA</option>
                                </select>
                            </div>
                            <div className="modal-field">
                                <input
                                    className="modal-input"
                                    type="text"
                                    name="year"
                                    value={selectedPdfBook.yearOfRelease?.substring(0, 4) || ""}
                                    onChange={handleChange}
                                    placeholder="Year of issue"
                                />
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button className="modal-button" onClick={onClose}>Cancel</button>
                            <button
                                className="modal-button modal-btn-primary"
                                onClick={onSubmit}
                            >
                                Save
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
};

export default PdfBookModal;