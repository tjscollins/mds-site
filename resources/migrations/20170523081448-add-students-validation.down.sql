ALTER TABLE students
      DROP CONSTRAINT unique_id_required;
ALTER TABLE students
      ALTER COLUMN first_name DROP NOT NULL;
ALTER TABLE students
      ALTER COLUMN last_name DROP NOT NULL;
