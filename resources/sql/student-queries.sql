-- :name create-student! :! :n
-- :doc creates a new user record
INSERT INTO students
(id, first_name, last_name, bio_blurb)
VALUES (:id, :first_name, :last_name, :bio_blurb)

-- :name update-student! :! :n
-- :doc update an existing user record
UPDATE students
SET first_name = :first_name, last_name = :last_name, bio_blurb=:bio_blurb
WHERE id = :id

-- :name get-students :? :1
-- :doc retrieve a user given the id.
SELECT * FROM students
WHERE id = :id

-- :name delete-student! :! :n
-- :doc delete a user given the id
DELETE FROM students
WHERE id = :id
