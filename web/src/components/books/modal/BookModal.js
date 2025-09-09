import React, {useState} from "react";
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
                       places
                      }) =>
{
    const [isTreeModalOpen, setIsTreeModalOpen] = useState(false);

    const handleChange = (e) => {
        if (e !== undefined && e.target !== undefined) {
            const {name, value} = e.target;
            setSelectedBook((prev) => ({...prev, [name]: value}));
        } else {
            onClose();
        }
    };

    const onNodeSelect = (nodePlace) => {
        console.log("nodePlace",  nodePlace);
        setSelectedBook({...selectedBook, place: nodePlace});
        setIsTreeModalOpen(false);
    };

    const closeTreeModal = () => {
        setIsTreeModalOpen(false);
    };

    const handleTreeSubmit = (e) => {
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
    }

    if (!isOpen) return null;

    const renderContent = () => {
        switch (modalType) {
            case "add":
            case "edit":
                return (
                    <div className="th-modal-overlay">
                        <div className="th-modal-content">

                            <div className="th-modal-header">
                                <h5>Add book</h5>
                            </div>
                            <div className="modal-body">
                                <form onSubmit={onSubmit}>
                                    <input placeholder="id" className="th-main-input" name="id" value={selectedBook.id} readOnly={true} disabled={true}/>
                                    <input className="th-main-input" type="text" name="title" value={selectedBook.title} onChange={handleChange} placeholder="Enter title of book"/>
                                    <input className="th-main-input" type="text" name="volume" value={selectedBook.volume} onChange={handleChange} placeholder="Enter volume of book"/>
                                    <select aria-label="Genre name" value={setSelectedBook.genre} onChange={(e) => {
                                        const selectedGenre = genres.find(g => g.id === Number(e.target.value));
                                        setSelectedBook(prev => ({ ...prev, genre: selectedGenre }));
                                    }} aria-placeholder="Select genre">
                                        <option value="" disabled hidden>Genre name</option>
                                        {genres.map((genre) => (
                                            <option key={genre.id} value={genre.id}>
                                                {genre.name}
                                            </option>
                                        ))}
                                    </select>
                                    <select multiple aria-label="Authors"
                                            value={selectedBook.authors?.map(a => a.id) || []} // value — массив id
                                                 onChange={(e) => {
                                                     const selectedIds = Array.from(e.target.selectedOptions, option => Number(option.value));
                                                     const selectedAuthors = authors.filter(author => selectedIds.includes(author.id));
                                                     setSelectedBook({...selectedBook, authors: selectedAuthors});
                                                 }}>
                                        <option value="" disabled hidden>Authors</option>
                                        {authors.map((author) => (
                                            <option key={author.id} value={author.id}>
                                                {author.name}
                                            </option>
                                        ))}
                                    </select>
                                    <select aria-label="Series" value={selectedBook.series}
                                                 onChange={(e) => setSelectedBook({ ...selectedBook, series: series.find(g => g.id === Number(e.target.value)) })}>
                                        {series.map((series) => (
                                            <option key={series.id} value={series.id}>
                                                {series.name}
                                            </option>
                                        ))}
                                    </select>
                                    {selectedBook.place !== null && selectedBook.place !== undefined ? (
                                        <button type="button" className="th-main-button" onClick={onPlacesOpenDialogBox}>Place selected: {getFullPlacePath(selectedBook.place)} ✅</button>
                                    ) : (
                                        <button type="button" className="th-main-button" onClick={onPlacesOpenDialogBox}>What a place ❓</button>
                                    )}
                                    <input className="modal-input" type="text" name="year" onChange={handleChange} placeholder="Enter year of release" value={selectedBook.year.substring(0, 4)}/>
                                    <input className="modal-input" type="text" name="description" onChange={handleChange} placeholder="Description" value={selectedBook.description} maxLength="512"/>
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
            default:
                return <p>Unknown action</p>;
        }
    };
    return (
        <>
            <div className="th-modal-overlay">
                <div className="th-modal-content">{renderContent()}</div>
            </div>

            <PlaceModal
                places={places}
                onCrossClick={onNodeSelect}
                isOpen={isTreeModalOpen}
                onClose={closeTreeModal}
                onSubmit={handleTreeSubmit}
                onAddChild={null}
            />
        </>
    );
};

export default BookModal;