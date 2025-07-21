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
import {fetchBooks} from "./api/api";
import AddBookModal from "./modal/AddBookModal";


const sortMenu = [{id: 1, value: 'id', innerText: 'id', key: 'id'}, {
    id: 2,
    value: 'title',
    innerText: 'Title',
    key: 'id'
}, {
    id: 3,
    value: 'reverse',
    innerText: 'Reverse',
    key: 'id'
}, {id: 4, value: 'genre', innerText: 'Genre', key: 'id'}];

const INITIAL_SORT_MENU_TYPE = {sortType: 'id'};

export const Books = () => {
    const [sortType, setType] = useState(INITIAL_SORT_MENU_TYPE);
    const dispatch = useDispatch();
    const {books, loading, error} = useSelector(state => state.booksReducer);

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [modalType, setModalType] = useState(null);
    const [formData, setFormData] = useState({ name: "", email: "" });
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
        setFormData({ name: "", email: "" });
        setSelectedBook(null);
    };

    useEffect(() => {
        fetchBooks(dispatch);
    }, [dispatch]);

    const onSortSelect = (event) => {
        console.log("onSortSeleced", event.target.value);
        switch (event.target.value) {
            case "id":
                dispatch(sortBooksById());
                break;
            case "Title":
                dispatch(sortBooksByTitle());
                break;
            case "Reverse":
                dispatch(sortBooksByIdReverse());
                break;
            case "Genre":
                dispatch(sortBooksByGenre());
                break;
            default:
                break;
        }
        ;
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
                console.log("Adding book:", formData);
                // await axios.post("https://your-api-endpoint.com/books", formData);
            } else if (modalType === "delete") {
                console.log("Deleting book:", selectedBook.id);
                // await axios.delete(`https://your-api-endpoint.com/books/${selectedBook.id}`);
            } else if (modalType === "csv") {
                console.log("Uploading CSV:", formData.csv);
                // const formDataToSend = new FormData();
                // formDataToSend.append("file", formData.csv);
                // await axios.post("https://your-api-endpoint.com/books/csv", formDataToSend);
            }
            closeModal();
        } catch (error) {
            console.error("Error:", error);
        }
    };

    if (loading) return (
        <div className='Container'>

            <div className="main-container">
                <h3>Books component</h3>
                <p>Loading...</p>
            </div>
        </div>);
    if (error) return (
        <div className='Container'>

            <div className="main-container">
                <h3>Books component</h3>
                <p>Error: {error}</p>
            </div>
        </div>);

    return (
        <div className='Container'>

            <div className="main-container">
                <div className="d-flex justify-content-center align-items-center">
                    <h4>My books</h4>
                </div>
                <div className="d-flex flex-row align-items-center gap-3">
                    <ThSelect
                        onChange={onSortSelect}
                        defaultChecked={sortType}
                        values={sortMenu}
                        label="Sort by"
                        label_size={1}
                        input_size={1}
                        required={false}
                    />
                    <button className="thbtn-add" onClick={handleAddBook}>
                        Add book
                    </button>
                    <button className="thbtn-add" onClick={handleUploadCSV}>
                        CSV upload....
                    </button>
                    <button className="thbtn-del" onClick={handleDelBook}>
                        Delete book
                    </button>
                </div>
                <div className="tableContainer">
                    <table className="table">
                        <thead>
                        <tr>
                            <th onClick={() => dispatch(sortBooksById())}>ID &#x25be;&#x25b4;</th>
                            <th onClick={() => dispatch(sortBooksByTitle())}>Title &#x25be;&#x25b4;</th>
                            <th>Author</th>
                            <th onClick={() => dispatch(sortBooksByGenre())}>Genre &#x25be;&#x25b4;</th>
                            <th>Series</th>
                            <th>Publisher</th>
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
                                <td className="authors">
                                    {book.authors ?
                                        [...book.authors].sort((a, b) => a.name.localeCompare(b.name)).map(author => ( // Sort here!
                                            <p key={author.id}>{author.name}</p>
                                        ))
                                        : ''}
                                </td>
                                <td>{book.genre.name}</td>
                                <td>{book.series.name}</td>
                                <td>{book.publisher}</td>
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
                </div>
            </div>
            <AddBookModal
                isOpen={isModalOpen}
                onClose={closeModal}
                onSubmit={handleSubmit}
                formData={formData}
                setFormData={setFormData}
                modalType={modalType}
                selectedBook={selectedBook}
            />
        </div>
    );

}