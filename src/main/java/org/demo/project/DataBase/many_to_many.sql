SELECT pet_table.name
FROM pet AS pet_table
         JOIN pet_by_person AS pbp_table ON pet_table.id = pet_id
         JOIN person AS person_table on person_table.id = pbp_table.person_id
WHERE person_table.name = 'Paul'