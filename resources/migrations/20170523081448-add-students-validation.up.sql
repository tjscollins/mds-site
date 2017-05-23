ALTER TABLE students
      ADD CONSTRAINT unique_id_required PRIMARY KEY (id);
ALTER TABLE students
      ALTER COLUMN first_name SET NOT NULL;
ALTER TABLE students
      ALTER COLUMN last_name SET NOT NULL;
