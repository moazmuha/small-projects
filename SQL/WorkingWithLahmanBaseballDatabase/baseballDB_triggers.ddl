-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
-- DDL Additions
-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
DROP TABLE IF EXISTS PlayerEarning;
DROP TABLE IF EXISTS TotalSalaryExpenditures;
CREATE TABLE PlayerEarning AS (select playerID,sum(salary) AS total_income from salaries group by playerID);

CREATE TABLE TotalSalaryExpenditures
AS (SELECT teamid AS team, sum(salary) AS total_expenditures FROM salaries GROUP BY teamid
);

-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
-- DDL Constraints
-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
ALTER TABLE ONLY PlayerEarning
    ADD CONSTRAINT playerID_cons PRIMARY KEY (playerID);
ALTER TABLE ONLY PlayerEarning
    ADD CONSTRAINT playerID_foreign_key FOREIGN KEY (playerID) REFERENCES people(playerID) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE ONLY TotalSalaryExpenditures
    ADD CONSTRAINT team_id_pri PRIMARY KEY (team);

-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
-- BEGIN
-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

-- ++++++++++++++++++++
--  Trigger for the table PlayerEarning so that when a new salary record is 
--  inserted or updated in the salaries table it will update the total income of that player.
-- ++++++++++++++++++++

CREATE OR REPLACE FUNCTION add_salary()
   RETURNS TRIGGER AS $add_salary_trigger$
   BEGIN
      IF (NEW.salary>0) AND (NEW.playerID IN (SELECT playerID FROM PlayerEarning)) AND (OLD.salary>0)  
         THEN UPDATE PlayerEarning SET total_income=total_income+NEW.salary-OLD.salary WHERE playerID=NEW.playerID;
      ELSIF (NEW.salary>0) AND (NEW.playerID IN (SELECT playerID FROM PlayerEarning))
         THEN UPDATE PlayerEarning SET total_income=total_income+NEW.salary WHERE playerID=NEW.playerID;
      ELSIF (NEW.playerID NOT IN (SELECT playerID FROM PlayerEarning))
         THEN INSERT INTO PlayerEarning (playerID, total_income) VALUES (NEW.playerID, NEW.salary);
      END IF;
      RETURN NEW;
   END;
$add_salary_trigger$ LANGUAGE PLPGSQL;

CREATE TRIGGER add_salary_trigger BEFORE UPDATE OR INSERT ON
salaries FOR EACH ROW EXECUTE PROCEDURE add_salary();

-- ++++++++++++++++++++
--  Trigger for the table TotalSalaryExpenditures so that when a player’s salary is inserted
--  or updated in the salaries table it will update the total expenditures for the team that 
--  the player belongs to that year. Hint: you need to deal with the case where the team is newly formed.
-- ++++++++++++++++++++

CREATE OR REPLACE FUNCTION add_expense()
   RETURNS TRIGGER AS $add_expense_trigger$
   BEGIN 
      IF (NEW.salary>0) AND (NEW.teamid IN (SELECT team FROM TotalSalaryExpenditures)) AND (OLD.salary>0)
         THEN UPDATE TotalSalaryExpenditures SET total_expenditures=total_expenditures+NEW.salary-OLD.salary WHERE team=NEW.teamid;
      ELSIF (NEW.salary>0) AND (NEW.teamid IN (SELECT team FROM TotalSalaryExpenditures))
         THEN UPDATE TotalSalaryExpenditures SET total_expenditures=total_expenditures+NEW.salary WHERE team=NEW.teamid;
      ELSIF (NEW.teamid NOT IN (SELECT team FROM TotalSalaryExpenditures))
         THEN INSERT INTO TotalSalaryExpenditures (team, total_expenditures) VALUES (NEW.teamID, NEW.salary);
      END IF;
      RETURN NEW;
   END;
$add_expense_trigger$ LANGUAGE PLPGSQL;

CREATE TRIGGER add_expense_trigger BEFORE UPDATE OR INSERT ON
salaries FOR EACH ROW EXECUTE PROCEDURE add_expense();

-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
-- END
-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++