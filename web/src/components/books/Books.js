import React, { useEffect, useState } from 'react';
import ThSelect from "../layout/select/th-select";
import { sortBooksByTitle, sortBooksById, sortBooksByIdReverse, sortBooksByGenre } from "./reducer/BooksSlice";
import { fetchBooks, addNewBook, deleteBook, updateBook } from "./reducer/BooksSlice";
import { fetchAuthors } from "./reducer/AuthorsSlice";
import { fetchGenres } from "./reducer/GenresSlice";
import { fetchSeries } from "./reducer/SeriesSlice";
import { useDispatch, useSelector } from "react-redux";
import BookModal from "./modal/BookModal";
import { Paginator } from "../layout/pagination/Paginator";
import { fetchAllPlaces } from "../places/reducer/PlaceSlice";

const sortMenu = [
    { id: 1, value: 'id',      innerText: 'id',      key: 'id' },
    { id: 2, value: 'title',   innerText: 'Title',   key: 'id' },
    { id: 3, value: 'reverse', innerText: 'Reverse', key: 'id' },
    { id: 4, value: 'genre',   innerText: 'Genre',   key: 'id' },
];

const INITIAL_SORT_MENU_TYPE = 'id';

export const Books = () => {
    const [sortType, setType] = useState(INITIAL_SORT_MENU_TYPE);
    const dispatch = useDispatch();

    const { books, loading, error } = useSelector(state => state.booksReducer);
    const total = useSelector(state => state.booksReducer.total);
    const pageNumber =  useSelector(state => state.booksReducer.pageNumber);
    const pageSize = 15;

    const { series  } = useSelector(state => state.seriesReducer);
    const { genres  } = useSelector(state => state.genresReducer);
    const { authors } = useSelector(state => state.authorsReducer);
    const { places  } = useSelector(state => state.placeReducer);

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [modalType, setModalType] = useState(null);

    const emptyBook = {
        id: "", title: "", volume: "", genre: {},
        authors: [], series: {}, year: "", place: null, description: ""
    };
    const [selectedBook, setSelectedBook] = useState(emptyBook);

    useEffect(() => {
        dispatch(fetchSeries());
        dispatch(fetchGenres());
        dispatch(fetchAuthors());
        dispatch(fetchAllPlaces());
        dispatch(fetchBooks({ pageNumber: 0, pageSize }));
    }, [dispatch]);

    const openModal = (type) => {
        setModalType(type);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setModalType(null);
        setSelectedBook(emptyBook);
    };

    const onSortSelect = (event) => {
        setType(event.target.value);
        switch (event.target.value.toLowerCase()) {
            case "id":      dispatch(sortBooksById());      break;
            case "title":   dispatch(sortBooksByTitle());   break;
            case "reverse": dispatch(sortBooksByIdReverse()); break;
            case "genre":   dispatch(sortBooksByGenre());   break;
            default: break;
        }
    };

    const handleAddBook = () => {
        setSelectedBook(emptyBook);
        openModal("add");
    };

    const handleDelBook = (book) => {
        setSelectedBook(book);
        openModal("delete");
    };

    const handleEditBook = (book) => {
        setSelectedBook({ ...book });
        openModal("edit");
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (modalType === "add") {
                dispatch(addNewBook(selectedBook));
            } else if (modalType === "delete") {
                dispatch(deleteBook(selectedBook.id));
            } else if (modalType === "edit") {
                dispatch(updateBook({ id: selectedBook.id, book: selectedBook, pageNumber, pageSize }));
            }
            closeModal();
        } catch (error) {
            console.error("Error:", error);
        }
    };

    const onChangePage = (pageNumber, pageSize) => {
        console.log("page Number: {}, page size: {}", pageNumber, pageSize);
        dispatch(fetchBooks({ pageNumber, pageSize }));
    };

    if (loading) return (
        <div className='root'>
            <div className="main-container">
                <h3>Books component</h3>
                <p>Loading...</p>
            </div>
        </div>
    );

    if (error) return (
        <div className='root'>
            <div className="main-container">
                <h3>Books component</h3>
                <p>Error: {error}</p>
            </div>
        </div>
    );

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
                <button className="th-main-button" onClick={handleAddBook}>Add book</button>
                <button className="th-main-button" onClick={handleDelBook}>Delete book</button>
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
                    {books && books.length !== 0 ? books.map((book) => (
                        <tr key={book.id}>
                            <td>{book.id}</td>
                            <td>{book.title}</td>
                            <td>{book.volume}</td>
                            <td>
                                {book.authors
                                    ? [...book.authors].sort((a, b) => a.name.localeCompare(b.name)).map(a => a.name).join(", ")
                                    : ''}
                            </td>
                            <td>{book.genre?.name}</td>
                            <td>{book.series?.name}</td>
                            <td>{book.year.substring(0, 4)}</td>
                            <td>{book.place.parent?.name + ":" + book.place.name}</td>
                            <td>{book.description}</td>
                            <td>
                                <button className="table-action-btn edit-btn" title="Редактировать"
                                        onClick={() => handleEditBook(book)}>✏️</button>
                            </td>
                            <td>
                                <button className="table-action-btn delete-btn" title="Удалить"
                                        onClick={() => handleDelBook(book)}>🗑️</button>
                            </td>
                        </tr>
                    )) : (
                        <tr>
                            <td colSpan="10" style={{ textAlign: "center" }}>
                                <h5>Book list is empty</h5>
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
                <Paginator
                    pageNumber={pageNumber}
                    totalPages={total}
                    pageSize={pageSize}
                    onChangePage={onChangePage}
                />
            </section>

            <BookModal
                isOpen={isModalOpen}
                onClose={closeModal}
                onSubmit={handleSubmit}
                modalType={modalType}
                selectedBook={selectedBook}
                setSelectedBook={setSelectedBook}
                genres={genres}
                series={series}
                authors={authors}
                places={places}
            />
        </main>
    );
};
