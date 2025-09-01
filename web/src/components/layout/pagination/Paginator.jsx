import { useState } from "react";
import './paginator.css';

export const Paginator = ({ onChagePage, total, pageSize }) => {
    const [pageNumber, setPageNumber] = useState(0);

    const increment = () => {
        if (pageNumber < total - 1) {
            const newPage = pageNumber + 1;
            setPageNumber(newPage);
            onChagePage(newPage, pageSize);
        }
    };

    const decrement = () => {
        if (pageNumber > 0) {
            const newPage = pageNumber - 1;
            setPageNumber(newPage);
            onChagePage(newPage, pageSize);
        }
    };

    return (
        <div className="paginatorContainer">
            <div>
                <button
                    type="button"
                    className="th-main-button"
                    onClick={decrement}
                >
                    {pageNumber > 0 ? "👈" : "✋"}
                </button>
            </div>
            <div>
                <input
                    className="paginatorInput"
                    value={pageNumber}
                    onChange={(e) => {
                        const newPage = Number(e.target.value) || 0;
                        setPageNumber(newPage);
                        onChagePage(newPage, pageSize);
                    }}
                />
            </div>
            <div>
                <button
                    type="button"
                    className="th-main-button"
                    onClick={increment}
                >
                    {pageNumber < total - 1 ? "👉" : "🤚"}
                </button>
            </div>
        </div>
    );
};