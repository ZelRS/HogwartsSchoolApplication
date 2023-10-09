ALTER TABLE студент
    ADD CONSTRAINT age_constraint CHECK (age > 16 ),
    ALTER COLUMN name SET NOT NULL,
    ADD CONSTRAINT name_constraint UNIQUE (name);

ALTER TABLE факультет
    ADD CONSTRAINT name_color_constraint UNIQUE (name, color);

ALTER TABLE студент
    ALTER COLUMN age SET DEFAULT 20;