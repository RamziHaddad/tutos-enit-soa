---
--- drop tables
---



DROP TABLE IF EXISTS persons;

--
-- Name: categories; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE persons (
    id uuid NOT NULL,
    firstName character varying(50) NOT NULL,
    lastName character varying(50) NOT NULL,
    age smallint
);

INSERT INTO public.persons(id,firstname, lastname, age)	VALUES ('a037b3be-fea3-4e17-84ba-ebe458d11222','ramzi','haddad',20);
INSERT INTO public.persons(id,firstname, lastname, age)	VALUES ('b157b3be-fee3-4e17-84aa-ebe458d11235','foulen','fouleni',25);
