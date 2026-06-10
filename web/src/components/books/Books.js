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

const GenreBadge = ({ name }) => {
    if (!name) return null;
    const lower = name.toLowerCase();
    let cls = "badge";
    if (lower === "роман")         cls += " badge-roman";
    else if (lower === "фэнтези")  cls += " badge-fantasy";
    else if (lower === "фантастика") cls += " badge-sci";
    return <span className={cls}>{name}</span>;
};

export const Books = () => {
    const [sortType, setType] = useState(INITIAL_SORT_MENU_TYPE);
    const dispatch = useDispatch();

    const { books, loading, error } = useSelector(state => state.booksReducer);
    const total      = useSelector(state => state.booksReducer.total);
    const pageNumber = useSelector(state => state.booksReducer.pageNumber);
    const pageSize   = 15;

    const { series  } = useSelector(state => state.seriesReducer);
    const { genres  } = useSelector(state => state.genresReducer);
    const { authors } = useSelector(state => state.authorsReducer);
    const { places  } = useSelector(state => state.placeReducer);

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [modalType,   setModalType]   = useState(null);

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

    const openModal  = (type) => { setModalType(type); setIsModalOpen(true); };

    const closeModal = () => {
        setIsModalOpen(false);
        setModalType(null);
        setSelectedBook(emptyBook);
    };

    const onSortSelect = (event) => {
        setType(event.target.value);
        switch (event.target.value.toLowerCase()) {
            case "id":      dispatch(sortBooksById());        break;
            case "title":   dispatch(sortBooksByTitle());     break;
            case "reverse": dispatch(sortBooksByIdReverse()); break;
            case "genre":   dispatch(sortBooksByGenre());     break;
            default: break;
        }
    };

    const handleAddBook  = ()     => { setSelectedBook(emptyBook); openModal("add"); };
    const handleDelBook  = (book) => { setSelectedBook(book);      openModal("delete"); };
    const handleEditBook = (book) => { setSelectedBook({ ...book }); openModal("edit"); };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if      (modalType === "add")    dispatch(addNewBook(selectedBook));
            else if (modalType === "delete") dispatch(deleteBook(selectedBook.id));
            else if (modalType === "edit")   dispatch(updateBook({ id: selectedBook.id, book: selectedBook, pageNumber, pageSize }));
            closeModal();
        } catch (err) {
            console.error("Error:", err);
        }
    };

    const onChangePage = (pageNumber, pageSize) => {
        dispatch(fetchBooks({ pageNumber, pageSize }));
    };

    if (loading) return (
        <div className="main-container" style={{ padding: 32, color: "#6b7280" }}>
            Loading…
        </div>
    );

    if (error) return (
        <div className="main-container" style={{ padding: 32, color: "#b91c1c" }}>
            Error: {error}
        </div>
    );

    return (
        <main className="main-container">
            {/* ── Toolbar ── */}
            <nav className="th-buttons-toolbar" aria-label="Books toolbar">
                <ThSelect
                    onChange={onSortSelect}
                    defaultChecked={sortType}
                    values={sortMenu}
                    label="Sort by"
                    label_size={1}
                    input_size={1}
                    required={false}
                />
                <div className="toolbar-divider" aria-hidden="true" />
                <button className="thbtn-add" onClick={handleAddBook}>
                    + Add book
                </button>
                <button className="thbtn-del" onClick={() => handleDelBook(selectedBook)}>
                    Delete book
                </button>
            </nav>

            {/* ── Table ── */}
            <section className="tableContainer">
                <table className="table">
                    <thead>
                    <tr>
                        <th onClick={() => dispatch(sortBooksById())}    style={{ cursor: "pointer" }}>ID</th>
                        <th onClick={() => dispatch(sortBooksByTitle())}  style={{ cursor: "pointer" }}>Title</th>
                        <th style={{ color: "#9ca3af", width: "120px" }}>Volume</th>
                        <th>Author</th>
                        <th onClick={() => dispatch(sortBooksByGenre())}  style={{ cursor: "pointer" }}>Genre</th>
                        <th>Series</th>
                        <th>Year</th>
                        <th>Place</th>
                        <th>Description</th>
                        <th style={{ textAlign: "center" }}>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {books && books.length > 0 ? books.map((book) => (
                        <tr key={book.id}>
                            <td style={{ color: "#9ca3af" }}>{book.id}</td>
                            <td style={{ fontWeight: 500 }}>{book.title}</td>
                            <td style={{ color: "#9ca3af", width: "120px" }}>{book.volume}</td>
                            <td>
                                {book.authors
                                    ? [...book.authors].sort((a, b) => a.name.localeCompare(b.name)).map(a => a.name).join(", ")
                                    : ""}
                            </td>
                            <td><GenreBadge name={book.genre?.name} /></td>
                            <td style={{ color: "#6b7280" }}>{book.series?.name}</td>
                            <td style={{ color: "#6b7280" }}>{book.year?.substring(0, 4)}</td>
                            <td style={{ color: "#6b7280" }}>
                                {book.place?.parent?.name
                                    ? `${book.place.parent.name} · ${book.place.name}`
                                    : book.place?.name}
                            </td>
                            <td style={{ color: "#6b7280" }}>{book.description}</td>
                            <td>
                                <div style={{ display: "flex", gap: 4, justifyContent: "center" }}>
                                    <button
                                        className="table-action-btn edit-btn"
                                        title="Edit"
                                        onClick={() => handleEditBook(book)}
                                        aria-label="Edit book"
                                    >
                                        ✎
                                    </button>
                                    <button
                                        className="table-action-btn delete-btn"
                                        title="Delete"
                                        onClick={() => handleDelBook(book)}
                                        aria-label="Delete book"
                                    >
                                        ✕
                                    </button>
                                </div>
                            </td>
                        </tr>
                    )) : (
                        <tr>
                            <td colSpan="10" style={{ textAlign: "center", padding: "32px 0", color: "#9ca3af" }}>
                                No books found
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
