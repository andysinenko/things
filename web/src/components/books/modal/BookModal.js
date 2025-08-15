import React from "react";

const BookModal = ({
                          isOpen,
                          onClose,
                          onSubmit,
                          formData,
                          setFormData,
                          modalType,
                          selectedBook,
                          genres,
                          series,
                          authors = []
                      }) => {
    if (!isOpen) return null;

    const handleChange = (e) => {
        if (e !== undefined && e.target !== undefined) {
            const {name, value} = e.target;
            setFormData((prev) => ({...prev, [name]: value}));
        } else {
            onClose();
        }
    };


    const renderContent = () => {
        switch (modalType) {
            case "add":
                return (
                    <div className="th-modal-overlay">
                        <div className="th-modal-content">

                            <div className="th-modal-header">
                                <h5>Add book</h5>
                            </div>
                            <div className="modal-body">
                                <form onSubmit={onSubmit}>
                                    <input placeholder="id" className="th-main-input" name="id" value={formData.id} />
                                        <input className="th-main-input" type="text" name="title" value={formData.title} onChange={handleChange} placeholder="Enter title of book"/>
                                    <select aria-label="Genre name" value={formData.genre} onChange={(e) => setFormData({ ...formData, genre: e.target.value })} aria-placeholder="Select genre">
                                        <option value="" disabled hidden>Genre name</option>
                                        {genres.map((genre) => (
                                            <option key={genre.id} value={genre.id}>
                                                {genre.name}
                                            </option>
                                        ))}
                                    </select>
                                    <select multiple aria-label="Authors"
                                                 value={formData.author.map(a => a.id)} // value — массив id
                                                 onChange={(e) => {
                                                     const selectedIds = Array.from(e.target.selectedOptions, option => Number(option.value));
                                                     const selectedAuthors = authors.filter(author => selectedIds.includes(author.id));
                                                     setFormData({...formData, author: selectedAuthors});
                                                 }}>
                                        <option value="" disabled hidden>Authors</option>
                                        {authors.map((author) => (
                                            <option key={author.id} value={author.id}>
                                                {author.name}
                                            </option>
                                        ))}
                                    </select>
                                    <select aria-label="Series" value={formData.series}
                                                 onChange={(e) => setFormData({
                                                     ...formData,
                                                     series: e.target.value
                                                 })}>
                                        {series.map((series) => (
                                            <option key={series.id} value={series.id}>
                                                {series.name}
                                            </option>
                                        ))}
                                    </select>
                                    <input className="modal-input" type="text" name="year" onChange={handleChange} placeholder="Enter year of release" value={formData.year}/>
                                    <input className="modal-input" type="text" name="description" onChange={handleChange} placeholder="Enter description" value={formData.description}/>
                                </form>
                            </div>
                            <div className="modal-footer">
                                <button className="th-main-button" onClick={onClose}>Close</button>
                                <button className="th-main-button" onClick={onSubmit}>Save Changes</button>
                            </div>
                        </div>
                    </div>
                );
            case "delete":
                return (
                    <div className="th-modal-overlay">
                        <div className="th-modal-content">
                            <div className="th-modal-header">
                                <h5>Delete book</h5>
                            </div>
                            <div className="modal-body">
                                <form onSubmit={onSubmit}>
                                    <span>Are you sure you want to delete "{selectedBook?.title || "this book"}"?</span>
                                </form>
                            </div>
                            <div className="modal-footer">
                                <button className="th-main-button" onClick={onClose}>Close</button>
                                <button className="th-main-button" onClick={onSubmit}>Save Changes</button>
                            </div>
                        </div>
                    </div>
                );
            case "csv":
                return (
                    <div className="th-modal-overlay">
                        <div className="th-modal-content">
                            <div className="th-modal-header">
                                <h5>CSV upload....</h5>
                            </div>
                            <div className="modal-body">
                                <form onSubmit={onSubmit}>
                                    <input className="modal-input" type="file" name="csv" accept=".csv"
                                                  onChange={(e) => setFormData((prev) => ({
                                                      ...prev,
                                                      csv: e.target.files[0]
                                                  }))}
                                                  required placeholder="Choose your csv file"/>
                                </form>
                            </div>
                            <div className="modal-footer">
                                <button className="th-main-button" onClick={onClose}>Close</button>
                                <button className="th-main-button" onClick={onSubmit}>Save Changes</button>
                            </div>
                        </div>
                    </div>);
            default:
                return <p>Unknown action</p>;
        }
    };
    return (
        <div className="th-modal-overlay">
            <div className="th-modal-content">{renderContent()}</div>
        </div>
    );
};

export default BookModal;