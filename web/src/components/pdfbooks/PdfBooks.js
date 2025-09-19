import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {fetchCategories, fetchPdfAuthors, fetchPdfBooks} from "./api/api";

export const PdfBooks = () => {
    const dispatch = useDispatch();
    const pageSize = 15;
    const pageNumber =  useSelector(state => state.pdfBooksReducer.pdfbooks.pageNumber);
    const {pdfauthors, pdfAuthLoading, pdfAuthError} = useSelector(state => state.pdfAuthorsReducer);
    const {categories, catLoading, catError} = useSelector(state => state.categoriesReducer);
    const {pdfbooks, loading, error} = useSelector(state => state.pdfBooksReducer);
    const [storingBook, setStortingBook] = useState({
        file: null,
        title: null,
        category: null,
        authors: [],
        yearOfRelease: null,
        language: null
    });

    useEffect(() => {
        dispatch(fetchPdfBooks(0, pageSize));
        dispatch(fetchCategories());
        dispatch(fetchPdfAuthors());
    }, [dispatch])

    const handleChange = (e) => {
        if (e !== undefined && e.target !== undefined) {
            const {name, value} = e.target;
            setStortingBook((prev) => ({...prev, [name]: value}));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!storingBook.file || !storingBook.title || !storingBook.author || !storingBook.year) {
            alert("Input all data");
            return;
        }
        const formData = new FormData();
        formData.append("file", storingBook.file);
        formData.append("title", storingBook.title);
        formData.append("category", storingBook.category);
        formData.append("author", storingBook.authors);
        formData.append("yearOfRelease", storingBook.yearOfRelease);
        formData.append("author", storingBook.language);

        /*try {
            const res = await fetch("/api/books", {
                method: "POST",
                body: formData,
            });
            if (res.ok) {
                const newBook = await res.json();
                setBooks(prev => [...prev, newBook]);
                setFile(null);
                setTitle("");
                setAuthor("");
                setYear("");
            } else {
                alert("Ошибка при загрузке книги");
            }
        } catch (err) {
            console.error(err);
            alert("Ошибка при отправке запроса");
        }*/
    };


    return(
        <main className="main-container">
            <nav className="th-buttons-toolbar" title='Pdf books'>
                <form onSubmit={handleSubmit} style={{ marginBottom: "20px" }}>
                    <input id="file"  type="file" placeholder="select file" value={storingBook.file} onChange={(e) => setStortingBook({...storingBook, file: e.target.files[0]}) }/>
                    <input id="title" type="text" className="th-main-input" name="book name" value={storingBook.title} onChange={handleChange}/>
                    <select id="authors" multiple={true} className="th-main-input" name="authors" aria-label="Authors" value={storingBook.authors}
                            onChange={(e) => {
                                console.log(e.target.selectedOptions);
                                const selectedIds = Array.from(e.target.selectedOptions, option => Number(option.id));
                                const selectedAuthors = pdfauthors.filter(author => selectedIds.includes(author.id));
                                setStortingBook({...storingBook, authors: selectedAuthors});
                            }}>
                        <option value="" disabled hidden>Authors</option>
                        {
                            pdfauthors.map((pdfAuthor) =>
                                <option key={pdfAuthor.id} value={pdfAuthor}>{pdfAuthor.name}</option>
                            )
                        }
                    </select>
                    <select className="th-main-input" name="category" aria-label="Category" value={storingBook.category}
                            onChange={(e) => {
                                console.log(e.target.value);
                                setStortingBook({...storingBook, category: category})
                            }}>
                        <option value="" disabled hidden>Category</option>
                        {
                            pdfauthors.map((category) =>
                                <option key={category.id} value={category}>{category.name}</option>
                            )
                        }
                    </select>
                </form>
            </nav>

            <section className="tableContainer">
                <table className="table">
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Authors</th>
                            <th>Category</th>
                            <th>Year of issue</th>
                        </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </section>
        </main>
    );
};