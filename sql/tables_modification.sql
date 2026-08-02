ALTER TABLE genres ADD COLUMN is_deleted boolean NOT NULL DEFAULT false;
ALTER TABLE genres
    ADD COLUMN created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    ADD COLUMN updated_at TIMESTAMPTZ NOT NULL DEFAULT now();

ALTER TABLE authors ADD COLUMN is_deleted boolean NOT NULL DEFAULT false;
ALTER TABLE authors
    ADD COLUMN created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    ADD COLUMN updated_at TIMESTAMPTZ NOT NULL DEFAULT now();

ALTER TABLE series ADD COLUMN is_deleted boolean NOT NULL DEFAULT false;
ALTER TABLE series
    ADD COLUMN created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    ADD COLUMN updated_at TIMESTAMPTZ NOT NULL DEFAULT now();

ALTER TABLE tools ADD COLUMN is_deleted boolean NOT NULL DEFAULT false;
ALTER TABLE tools
    ADD COLUMN created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    ADD COLUMN updated_at TIMESTAMPTZ NOT NULL DEFAULT now();

ALTER TABLE vendors ADD COLUMN is_deleted boolean NOT NULL DEFAULT false;
ALTER TABLE vendors
    ADD COLUMN created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    ADD COLUMN updated_at TIMESTAMPTZ NOT NULL DEFAULT now();

ALTER TABLE pdf_books ADD COLUMN is_deleted boolean NOT NULL DEFAULT false;
ALTER TABLE pdf_books
    ADD COLUMN created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    ADD COLUMN updated_at TIMESTAMPTZ NOT NULL DEFAULT now();

ALTER TABLE pdfbooks_authors ADD COLUMN is_deleted boolean NOT NULL DEFAULT false;
ALTER TABLE pdfbooks_authors
    ADD COLUMN created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    ADD COLUMN updated_at TIMESTAMPTZ NOT NULL DEFAULT now();

ALTER TABLE pdfbooks_categories ADD COLUMN is_deleted boolean NOT NULL DEFAULT false;
ALTER TABLE pdfbooks_categories
    ADD COLUMN created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    ADD COLUMN updated_at TIMESTAMPTZ NOT NULL DEFAULT now();

ALTER TABLE books
    ADD COLUMN is_deleted boolean NOT NULL DEFAULT false,
    ADD COLUMN created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    ADD COLUMN updated_at TIMESTAMPTZ NOT NULL DEFAULT now();