import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from "react-redux";
import {deletePdfBook, fetchCategories, fetchPdfAuthors, fetchPdfBooks, updatePdfBook, uploadPdfBook} from "./reducer/PdfBooksSlice";
import PdfBookModal from "./modal/PdfBookModal";
import {Paginator} from "../layout/pagination/Paginator";
import {fetchBooks} from "../books/reducer/BooksSlice";


const INITIAL_BOOK = {
    file: null,
    title: '',
    category: '',
    author: {},
    yearOfRelease: '',
    language: ''
};

const YEARS = Array.from({ length: 26 }, (_, i) => 2000 + i);

export const PdfBooks = () => {
    const dispatch   = useDispatch();

    //modal window
    const [isOpen, setIsOpen] = useState(false);
    const emptyPdfBook = {
        id: '',
        title: '',
        category: '',
        author: '',
        yearOfRelease: '',
        language: ''
    };
    const [selectedPdfBook, setSelectedPdfBook] = useState(emptyPdfBook);

    const { pdfbooks, pageNumber, total } = useSelector(state => state.pdfBooksReducer.pdfbooks);
    const pageSize   = 15;
    const categories = useSelector(state => state.pdfBooksReducer.categories);
    const pdfauthors = useSelector(state => state.pdfBooksReducer.pdfAuthors);
    const loading    = useSelector(state => state.pdfBooksReducer.loading);
    const error      = useSelector(state => state.pdfBooksReducer.error);

    const [storingBook, setStoringBook] = useState(INITIAL_BOOK);
    const [fileName, setFileName]       = useState('');

    useEffect(() => {
        dispatch(fetchPdfBooks({ pageNumber: 0, pageSize }));
        dispatch(fetchCategories());
        dispatch(fetchPdfAuthors());
    }, [dispatch]);

    const handleChange = (e) => {
        if (!e?.target) return;
        const { name, value } = e.target;
        setStoringBook(prev => ({ ...prev, [name]: value }));
    };

    const handleFileChange = (e) => {
        const file = e.target.files[0];
        setStoringBook(prev => ({ ...prev, file }));
        setFileName(file ? file.name : '');
    };

    const handleDelBook = (book) => {
        dispatch(deletePdfBook(book.id));
    };

    const handleSubmit = async (e) => {
        console.log("Uploading pdfbook...");
        e.preventDefault();
        const formData = new FormData();
        formData.append("file",          storingBook.file);
        formData.append("title",         storingBook.title);
        formData.append("category",      storingBook.category);
        formData.append("author",        storingBook.author?.id ?? '');
        formData.append("yearOfRelease", storingBook.yearOfRelease);
        formData.append("language",      storingBook.language);

        await dispatch(uploadPdfBook(formData));
        setStoringBook(INITIAL_BOOK);
        setFileName('');
        dispatch(fetchPdfBooks({ pageNumber: 0, pageSize }));
    };

    const openModal  = (pdfBook) => {
        console.log(pdfBook);
        setSelectedPdfBook(pdfBook);
        setIsOpen(true);
    };

    const onClose = (e) => {
        console.log("onClose ", e);
        setIsOpen(false);
        setSelectedPdfBook(emptyPdfBook);
    }

    const handleUpdateSubmit = async (e) => {
        console.log("Updating pdfbook...", selectedPdfBook);
        await dispatch(updatePdfBook({ id: selectedPdfBook.id, book: selectedPdfBook }));
        setIsOpen(false);
        setSelectedPdfBook(emptyPdfBook);
        dispatch(fetchPdfBooks({ pageNumber: 0, pageSize }));
    };

    if (loading) return (
        <div className="main-container" style={{ padding: 32, color: "#6b7280" }}>Loading…</div>
    );

    if (error) return (
        <div className="main-container" style={{ padding: 32, color: "#b91c1c" }}>Error: {error}</div>
    );

    const onChangePage = (pageNumber, pageSize) => {
        dispatch(fetchBooks({ pageNumber, pageSize }));
    };

    return (
        <main className="main-container">

            {/* ── Toolbar / Upload form ── */}
            <nav className="th-buttons-toolbar" aria-label="PDF books upload">

                {/* File picker */}
                <label style={{ display: "inline-flex", alignItems: "center", gap: 8 }}>
                    <input
                        id="file"
                        type="file"
                        accept=".pdf"
                        style={{ display: "none" }}
                        onChange={handleFileChange}
                    />
                    <span
                        className="th-main-button"
                        onClick={() => document.getElementById('file').click()}
                        style={{ cursor: "pointer" }}
                    >
                        + Choose file
                    </span>
                    {fileName && (
                        <span style={{ fontSize: 12, color: "#6b7280", maxWidth: 160, overflow: "hidden", textOverflow: "ellipsis", whiteSpace: "nowrap" }}>
                            {fileName}
                        </span>
                    )}
                </label>

                <div className="toolbar-divider" aria-hidden="true" />

                {/* Title */}
                <input
                    type="text"
                    className="th-main-input"
                    name="title"
                    value={storingBook.title}
                    placeholder="Title"
                    onChange={handleChange}
                    style={{ width: 160 }}
                />

                {/* Year */}
                <select
                    name="yearOfRelease"
                    value={storingBook.yearOfRelease}
                    onChange={handleChange}
                    className="th-main-input"
                    style={{ width: 96 }}
                >
                    <option value="" disabled hidden>Year</option>
                    {YEARS.map(y => <option key={y} value={y}>{y}</option>)}
                </select>

                {/* Category */}
                <select
                    name="category"
                    aria-label="Category"
                    value={storingBook.category?.id || storingBook.category || ''}
                    onChange={(e) => setStoringBook(prev => ({ ...prev, category: Number(e.target.value) }))}
                    className="th-main-input"
                    style={{ width: 130 }}
                >
                    <option value="" disabled hidden>Category</option>
                    {categories?.map(cat => (
                        <option key={cat.id} value={cat.id}>{cat.name}</option>
                    ))}
                </select>

                {/* Author */}
                <select
                    name="authors"
                    aria-label="Author"
                    value={storingBook.author?.id || ''}
                    onChange={(e) => {
                        const selected = pdfauthors?.find(a => a.id === Number(e.target.value));
                        setStoringBook(prev => ({ ...prev, author: selected }));
                    }}
                    className="th-main-input"
                    style={{ width: 150 }}
                >
                    <option value="" disabled hidden>Author</option>
                    {pdfauthors?.map(a => (
                        <option key={a.id} value={a.id}>{a.name}</option>
                    ))}
                </select>

                {/* Language */}
                <select
                    name="language"
                    value={storingBook.language}
                    onChange={handleChange}
                    className="th-main-input"
                    style={{ width: 80 }}
                >
                    <option value="" disabled hidden>Lang</option>
                    <option value="EN">EN</option>
                    <option value="RU">RU</option>
                </select>

                <div className="toolbar-divider" aria-hidden="true" />

                <button type="button" className="thbtn-add" onClick={handleSubmit}>
                    Upload
                </button>
            </nav>

            {/* ── Table ── */}
            <section className="tableContainer">
                <table className="table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Category</th>
                        <th>Author</th>
                        <th>Year</th>
                        <th>Language</th>
                        <th style={{ textAlign: "center" }}>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {pdfbooks && pdfbooks.length > 0 ? pdfbooks.map(book => (
                        <tr key={book.id}>
                            <td style={{ color: "#9ca3af" }}>{book.id}</td>
                            <td style={{ fontWeight: 500 }}>{book.title}</td>
                            <td style={{ color: "#6b7280" }}>{book.category?.name}</td>
                            <td style={{ color: "#6b7280" }}>{book.author?.name}</td>
                            <td style={{ color: "#6b7280" }}>{book.yearOfRelease?.substring(0, 4)}</td>
                            <td style={{ color: "#6b7280" }}>
                                {book.language && (
                                    <span className="badge" style={{
                                        background: book.language === "EN" ? "#eff6ff" : "#f0fdf4",
                                        color:      book.language === "EN" ? "#1d4ed8" : "#166534",
                                    }}>{book.language}
                                    </span>
                                )}
                            </td>
                            <td>
                                <div style={{ display: "flex", gap: 4, justifyContent: "center" }}>
                                    <button
                                        className="table-action-btn edit-btn"
                                        title="Edit"
                                        aria-label="Edit book"
                                        onClick={() => openModal(book)}
                                    >
                                        ✎
                                    </button>
                                    <button
                                        className="table-action-btn delete-btn"
                                        title="Delete"
                                        aria-label="Delete book"
                                        onClick={() => handleDelBook(book)}
                                    >
                                        ✕
                                    </button>
                                </div>
                            </td>
                        </tr>
                    )) : (
                        <tr>
                            <td colSpan="7" style={{ textAlign: "center", padding: "32px 0", color: "#9ca3af" }}>
                                No PDF books found
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
            <PdfBookModal
                isOpen={isOpen}
                onClose={onClose}
                selectedPdfBook={selectedPdfBook}
                setSelectedPdfBook={setSelectedPdfBook}
                categories={categories}
                authors={pdfauthors}
                onSubmit={handleUpdateSubmit}
            />
        </main>
    );
};
