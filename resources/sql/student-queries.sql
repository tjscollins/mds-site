-- :name create-student! :! :n
-- :doc creates a new student record
INSERT INTO students
       (id, 
       first_name, 
       last_name, 
       bio_blurb, 
       bio_photo,
       stry_photo_1,
       stry_photo_2,
       stry_photo_3)
VALUES (:id, 
       :first_name, 
       :last_name, 
       :bio_blurb, 
       :bio_photo,
       :stry_photo_1,
       :stry_photo_2,
       :stry_photo_3)

-- :name update-student! :! :n
-- :doc update an existing student record
UPDATE students
SET first_name = :first_name,
    last_name = :last_name,
    bio_blurb=:bio_blurb,
    bio_photo=:bio_photo,
    stry_photo_1=:stry_photo_1,
    stry_photo_2=:stry_photo_2,
    stry_photo_3=:stry_photo_3

WHERE id = :id

-- :name get-student :? :1
-- :doc retrieve a student given the id.
SELECT * FROM students
WHERE id = :id

-- :name get-all-students :?
-- :doc retrieve a list of students
SELECT * FROM students

-- :name delete-student! :! :n
-- :doc delete a student given the id
DELETE FROM students
WHERE id = :id

-- :name clear-students! :!
-- :doc empty students table.  NOT TO BE USED IN PRODUCTION.
TRUNCATE ONLY students
