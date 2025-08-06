import React, {useEffect, useState} from 'react'

import ThSelect from "../layout/select/th-select";

import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faTrash} from '@fortawesome/free-solid-svg-icons';
import {
    sortBooksByTitle,
    sortBooksById,
    sortBooksByIdReverse,
    sortBooksByGenre
} from "./reducer/BooksSlice";
import {useDispatch, useSelector} from "react-redux";
import {addNewBook, deleteBook, fetchAuthors, fetchBooks, fetchGenres, fetchSeries} from "./api/api";
import BookModal from "./modal/BookModal";


const sortMenu = [
    {id: 1, value: 'id', innerText: 'id', key: 'id'},
    {id: 2, value: 'title', innerText: 'Title', key: 'id'},
    {id: 3, value: 'reverse', innerText: 'Reverse', key: 'id'},
    {id: 4, value: 'genre', innerText: 'Genre', key: 'id'}];

const INITIAL_SORT_MENU_TYPE = 'id';

export const Books = () => {
    const [sortType, setType] = useState(INITIAL_SORT_MENU_TYPE);
    const dispatch = useDispatch();

    const {books, loading, error} = useSelector(state => state.booksReducer);

    const {series, seriesLoading, seriesError} = useSelector(state => state.seriesReducer);
    const {genres, genresLoading, genresError} = useSelector(state => state.genresReducer);
    const {authors, authorsLoading, authorsError} = useSelector(state => state.authorsReducer);

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [modalType, setModalType] = useState(null);
    const [formData, setFormData] = useState({
        id: "",
        title: "",
        genre: "",
        author: [],
        series: "",
        year: "",
        place: "",
        description: ""
    });
    const [selectedBook, setSelectedBook] = useState(null);

    const openModal = (type, book = null) => {
        console.log("Opening modal with type:", type);
        setModalType(type);
        setSelectedBook(book);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setModalType(null);
        setFormData({id: "", title: "", genre: "", author: [], series: "", year: "", place: "", description: ""});
        setSelectedBook(null);
    };

    useEffect(() => {
        console.log("!!!!Books component rendered");
        dispatch(fetchSeries());
        dispatch(fetchGenres());
        dispatch(fetchAuthors());
        dispatch(fetchBooks());
    }, [dispatch]);

    const onSortSelect = (event) => {
        setType(event.target.value);
        switch (event.target.value.toLowerCase()) {
            case "id":
                dispatch(sortBooksById());
                break;
            case "title":
                dispatch(sortBooksByTitle());
                break;
            case "reverse":
                dispatch(sortBooksByIdReverse());
                break;
            case "genre":
                dispatch(sortBooksByGenre());
                break;
            default:
                break;
        }
    };

    const handleAddBook = () => {
        console.log("Opening Add Book Modal");
        openModal("add");
    };

    const handleDelBook = (book) => {
        console.log("Opening Delete Book Modal for:", book.id);
        openModal("delete", book);
    };

    const handleUploadCSV = () => {
        console.log("Opening CSV Upload Modal");
        openModal("csv");
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (modalType === "add") {
                const newBookObject =
                    {
                        id: "",
                        title: formData.title,
                        genre: formData.genre,
                        author: formData.author,
                        series: formData.series,
                        year: formData.year,
                        place: formData.place,
                        description: formData.description
                    };
                dispatch(addNewBook(newBookObject));
            } else if (modalType === "delete") {
                console.log("Deleting book:", selectedBook.id);
                dispatch(deleteBook(selectedBook.id));
            } else if (modalType === "csv") {
                console.log("Uploading CSV:", formData.csv);
            }
            closeModal();
        } catch (error) {
            console.error("Error:", error);
        }
    };

    if (loading) return (
        <div className='root'>

            <div className="main-container">
                <h3>Books component</h3>
                <p>Loading...</p>
            </div>
        </div>);
    if (error) return (
        <div className='root'>

            <div className="main-container">
                <h3>Books component</h3>
                <p>Error: {error}</p>
            </div>
        </div>);

    return (
        <main className="main-container">
            <nav className="th-buttons-toolbar" aria-label="Books">
                <ThSelect
                    onChange={onSortSelect}
                    defaultChecked={sortType}
                    values={sortMenu}
                    label="Sort by"
                    label_size={1}
                    input_size={1}
                    required={false}
                />
                <button className="th-main-button" variant="light" size="sm" onClick={handleAddBook}>
                    Add book
                </button>
                <button className="th-main-button" variant="light" size="sm" onClick={handleUploadCSV}>
                    CSV upload....
                </button>
                <button className="th-main-button" variant="light" size="sm" onClick={handleDelBook}>
                    Delete book
                </button>
            </nav>

            <section className="tableContainer">
                <table className="table">
                    <thead>
                    <tr>
                        <th onClick={() => dispatch(sortBooksById())}>ID &#x25be;&#x25b4;</th>
                        <th onClick={() => dispatch(sortBooksByTitle())}>Title &#x25be;&#x25b4;</th>
                        <th>Author</th>
                        <th onClick={() => dispatch(sortBooksByGenre())}>Genre &#x25be;&#x25b4;</th>
                        <th>Series</th>
                        <th>Year</th>
                        <th>Place</th>
                        <th>Description</th>
                        <th>Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    {books.length !== 0 ? books.map((book) =>
                        <tr key={book.id}>
                            <td>{book.id}</td>
                            <td>{book.title}</td>
                            <td>
                                {book.authors ?
                                    [...book.authors].sort((a, b) => a.name.localeCompare(b.name)).map(author => (author.name)).join(", ")
                                    : ''}
                            </td>
                            <td>{book.genre?.name}</td>
                            <td>{book.series?.name}</td>
                            <td>{book.year}</td>
                            <td>{book.place}</td>
                            <td>{book.description}</td>
                            <td>
                                <button onClick={() => handleDelBook(book)}
                                        style={{cursor: 'pointer', border: 'none', background: 'none'}}>
                                    <FontAwesomeIcon icon={faTrash} size="lg" color="red"/>
                                </button>
                            </td>
                        </tr>
                    ) : (
                        <tr>
                            <td colSpan="10" style={{textAlign: "center"}}>
                                <h3>Book list is empty</h3>
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </section>
            <BookModal
                isOpen={isModalOpen}
                onClose={closeModal}
                onSubmit={handleSubmit}
                formData={formData}
                setFormData={setFormData}
                modalType={modalType}
                selectedBook={selectedBook}
                header="Add book"
                genres={genres}
                series={series}
                authors={authors}
            />
        </main>);

}