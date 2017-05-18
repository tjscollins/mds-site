-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO students
(id, first_name, last_name, bio_blurb)
VALUES (:id, :first_name, :last_name, :bio_blurb)

-- :name update-user! :! :n
-- :doc update an existing user record
UPDATE students
SET first_name = :first_name, last_name = :last_name, bio_blurb=:bio_blurb
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieve a user given the id.
SELECT * FROM students
WHERE id = :id

-- :name delete-user! :! :n
-- :doc delete a user given the id
DELETE FROM students
WHERE id = :id

