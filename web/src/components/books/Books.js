import React, {useEffect, useState} from 'react'
import ThSelect from "../layout/select/th-select";

import {
    sortBooksByTitle,
    sortBooksById,
    sortBooksByIdReverse,
    sortBooksByGenre
} from "./reducer/BooksSlice";
import {useDispatch, useSelector} from "react-redux";
import {addNewBook, deleteBook, fetchAuthors, fetchBooks, fetchGenres, fetchSeries} from "./api/api";
import BookModal from "./modal/BookModal";
import {Paginator} from "../layout/pagination/Paginator";


const sortMenu = [
    {id: 1, value: 'id', innerText: 'id', key: 'id'},
    {id: 2, value: 'title', innerText: 'Title', key: 'id'},
    {id: 3, value: 'reverse', innerText: 'Reverse', key: 'id'},
    {id: 4, value: 'genre', innerText: 'Genre', key: 'id'}];

const INITIAL_SORT_MENU_TYPE = 'id';

export const Books = () => {
    const [sortType, setType] = useState(INITIAL_SORT_MENU_TYPE);
    const dispatch = useDispatch();

    const { books, loading, error } = useSelector(state => state.booksReducer.books);
    const total = useSelector(state => state.booksReducer.books.total);
    const pageNumber =  useSelector(state => state.booksReducer.books.pageNumber);
    const pageSize = 15;

    console.log("!!!!!!books: ", books);
    console.log("!!!!!!total: ", total);
    console.log("!!!!!!pageNumber: ", pageNumber);

    const [selectedPlace, setSelectedPlace] = useState(null);

    const {series, seriesLoading, seriesError} = useSelector(state => state.seriesReducer);
    const {genres, genresLoading, genresError} = useSelector(state => state.genresReducer);
    const {authors, authorsLoading, authorsError} = useSelector(state => state.authorsReducer);

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [modalType, setModalType] = useState(null);
    const [formData, setFormData] = useState({
        id: "",
        title: "",
        volume: "",
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
        setFormData({id: "", title: "", volume: "", genre: "", author: [], series: "", year: "", place: "", description: ""});
        setSelectedPlace(null);
        setSelectedBook(null);
    };

    useEffect(() => {
        dispatch(fetchSeries());
        dispatch(fetchGenres());
        dispatch(fetchAuthors());
        dispatch(fetchBooks(0, pageSize));
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
                        volume: formData.volume,
                        genre: formData.genre,
                        author: formData.author,
                        series: formData.series,
                        year: `${formData.year}-01-01`,
                        place: formData.place,
                        description: formData.description
                    };
                dispatch(addNewBook(newBookObject));
            } else if (modalType === "delete") {
                console.log("Deleting book:", selectedBook.id);
                dispatch(deleteBook(selectedBook.id));
            } else if (modalType === "csv") {
                console.log("Uploading CSV:", formData.csv);
            } else if (modalType === "edit") {
                const editedBookObject =
                    {
                        id: Number(formData.id),
                        title: formData.title,
                        volume: formData.volume,
                        genre: formData.genre,
                        author: formData.author,
                        series: formData.series,
                        year:  `${formData.year}-01-01`,
                        place: formData.place,
                        description: formData.description
                    };
                //dispatch(addNewBook(editedBookObject));
            }
            closeModal();
        } catch (error) {
            console.error("Error:", error);
        }
    };

    const handleEditBook = (book) => {
        console.log("!!! handleEditBook: ", book);
        const newSelectedBook = {
            id:             Number(book.id),
            title:          book.title,
            volume:         book.volume,
            genre:          book.genre,
            author:         book.author,
            series:         book.series,
            year:           book.year.substring(0, 4),
            place:         book.place,
            description:    book.description
        };
        setFormData(newSelectedBook);
        setSelectedPlace(book.place);
        openModal("edit", book);
    }

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

    const onChagePage = (pageNumber, pageSize) => {
        dispatch(fetchBooks(pageNumber, pageSize));
    };

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
                        <th>Volume</th>
                        <th>Author</th>
                        <th onClick={() => dispatch(sortBooksByGenre())}>Genre &#x25be;&#x25b4;</th>
                        <th>Series</th>
                        <th>Year</th>
                        <th>Place</th>
                        <th>Description</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    {books.length !== 0 ? books.map((book) =>
                        <tr key={book.id}>
                            <td>{book.id}</td>
                            <td>{book.title}</td>
                            <td>{book.volume}</td>
                            <td>
                                {book.authors ?
                                    [...book.authors].sort((a, b) => a.name.localeCompare(b.name)).map(author => (author.name)).join(", ")
                                    : ''}
                            </td>
                            <td>{book.genre?.name}</td>
                            <td>{book.series?.name}</td>
                            <td>{book.year}</td>
                            <td>{book.place.parent?.name + ":" + book.place.name}</td>
                            <td>{book.description}</td>
                            <td>
                                <button className="table-action-btn edit-btn" title="Редактировать" onClick={() => handleEditBook(book)}>✏️</button>
                            </td>
                            <td>
                                <button className="table-action-btn delete-btn" title="Удалить" onClick={() => handleDelBook(book)}>🗑️</button>
                            </td>
                        </tr>
                    ) : (
                        <tr>
                            <td colSpan="10" style={{textAlign: "center"}}>
                                <h5>Book list is empty</h5>
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
                <Paginator onChagePage = {onChagePage} total={total} pageSize={pageSize}/>
            </section>
            <BookModal
                isOpen={isModalOpen}
                onClose={closeModal}
                onSubmit={handleSubmit}
                formData={formData}
                setFormData={setFormData}
                modalType={modalType}
                selectedBook={selectedBook}
                genres={genres}
                series={series}
                authors={authors}
            />
        </main>);

}