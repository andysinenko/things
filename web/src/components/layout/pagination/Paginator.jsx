import './paginator.css';

export const Paginator = ({ pageNumber, totalPages, pageSize, onChangePage }) => {
    const isFirst = pageNumber <= 0;
    const isLast  = pageNumber >= totalPages - 1;

    const goTo = (page) => {
        if (page >= 0 && page < totalPages) {
            onChangePage(page, pageSize);
        }
    };

    const getPageNumbers = () => {
        if (totalPages <= 7) {
            return Array.from({ length: totalPages }, (_, i) => i);
        }
        const pages = new Set([0, totalPages - 1, pageNumber]);
        if (pageNumber > 0)           pages.add(pageNumber - 1);
        if (pageNumber < totalPages - 1) pages.add(pageNumber + 1);
        return [...pages].sort((a, b) => a - b);
    };

    const pageNums = getPageNumbers();

    return (
        <div className="paginatorContainer">
            <button
                type="button"
                className="pg-btn"
                onClick={() => goTo(pageNumber - 1)}
                disabled={isFirst}
                aria-label="Previous page"
            >
                &#8249;
            </button>

            {pageNums.map((page, i) => {
                const prev = pageNums[i - 1];
                const showEllipsis = prev !== undefined && page - prev > 1;
                return (
                    <span key={page} style={{ display: "inline-flex", alignItems: "center", gap: 4 }}>
                        {showEllipsis && (
                            <span className="pg-ellipsis">…</span>
                        )}
                        <button
                            type="button"
                            className={`pg-btn${page === pageNumber ? " active" : ""}`}
                            onClick={() => goTo(page)}
                            aria-label={`Page ${page + 1}`}
                            aria-current={page === pageNumber ? "page" : undefined}
                        >
                            {page + 1}
                        </button>
                    </span>
                );
            })}

            <button
                type="button"
                className="pg-btn"
                onClick={() => goTo(pageNumber + 1)}
                disabled={isLast}
                aria-label="Next page"
            >
                &#8250;
            </button>
        </div>
    );
};
