--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: goals; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE goals (
    id integer NOT NULL,
    description character varying,
    politicianid integer
);


ALTER TABLE goals OWNER TO "Guest";

--
-- Name: goals_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE goals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE goals_id_seq OWNER TO "Guest";

--
-- Name: goals_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE goals_id_seq OWNED BY goals.id;


--
-- Name: politicians; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE politicians (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE politicians OWNER TO "Guest";

--
-- Name: politicians_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE politicians_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE politicians_id_seq OWNER TO "Guest";

--
-- Name: politicians_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE politicians_id_seq OWNED BY politicians.id;


--
-- Name: reviews; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE reviews (
    id integer NOT NULL,
    goalid integer,
    description character varying
);


ALTER TABLE reviews OWNER TO "Guest";

--
-- Name: reviews_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE reviews_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE reviews_id_seq OWNER TO "Guest";

--
-- Name: reviews_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE reviews_id_seq OWNED BY reviews.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY goals ALTER COLUMN id SET DEFAULT nextval('goals_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY politicians ALTER COLUMN id SET DEFAULT nextval('politicians_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY reviews ALTER COLUMN id SET DEFAULT nextval('reviews_id_seq'::regclass);


--
-- Data for Name: goals; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY goals (id, description, politicianid) FROM stdin;
1	Build a shoot your friend	1
2	Blocks of cheese as far as the eye can see	2
3	A punch in the gut	3
4	Shoot thy arrow	5
5	Pierce thy skin	5
\.


--
-- Name: goals_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('goals_id_seq', 5, true);


--
-- Data for Name: politicians; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY politicians (id, name) FROM stdin;
1	Dick trump
2	Satchel
3	Cowface Gutpuncher
4	Hawkeye Spiderhulk
5	Gandorf Linkolas
\.


--
-- Name: politicians_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('politicians_id_seq', 5, true);


--
-- Data for Name: reviews; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY reviews (id, goalid, description) FROM stdin;
1	0	He did it
2	2	Not so great
3	3	As promised!
4	5	Success!
5	5	Failure!
\.


--
-- Name: reviews_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('reviews_id_seq', 5, true);


--
-- Name: goals_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY goals
    ADD CONSTRAINT goals_pkey PRIMARY KEY (id);


--
-- Name: politicians_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY politicians
    ADD CONSTRAINT politicians_pkey PRIMARY KEY (id);


--
-- Name: reviews_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY reviews
    ADD CONSTRAINT reviews_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

