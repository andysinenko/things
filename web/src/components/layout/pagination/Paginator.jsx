
import './paginator.css';

export const Paginator = ({ pageNumber, totalPages, pageSize, onChangePage }) => {

    /*const increment = () => {
        if (pageNumber < totalPages - 1) {
            const newPage = pageNumber + 1;
            onChangePage(newPage, pageSize);
        }
    };*/

    const increment = () => {
        if (pageNumber < totalPages - 1) {
            const newPage = pageNumber + 1;
            onChangePage(newPage, pageSize);
        }
    };

    const decrement = () => {
        if (pageNumber > 0) {
            const newPage = pageNumber - 1;
            onChangePage(newPage, pageSize);
        }
    };

    return (
        <div className="paginatorContainer">
            <div>
                <button
                    type="button"
                    className="th-main-button"
                    onClick={decrement}>
                    {pageNumber > 0 ? "👈" : "✋"}
                </button>
            </div>
            <div>
                <input
                    className="paginatorInput"
                    value={pageNumber}
                    onChange={(e) => {
                        const newPage = Number(e.target.value) || 0;
                        onChangePage(newPage, pageSize);
                    }}
                />
            </div>
            <div>
                <button
                    type="button"
                    className="th-main-button"
                    onClick={increment}
                >
                    {pageNumber < totalPages - 1 ? "👉" : "🤚"}
                </button>
            </div>
        </div>
    );
};