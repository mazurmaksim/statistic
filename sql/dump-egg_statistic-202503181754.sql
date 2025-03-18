--
-- PostgreSQL database cluster dump
--

-- Started on 2025-03-18 17:54:13

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--

CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS;

--
-- User Configurations
--








--
-- Databases
--

--
-- Database "template1" dump
--

\connect template1

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2025-03-18 17:54:13

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

-- Completed on 2025-03-18 17:54:13

--
-- PostgreSQL database dump complete
--

--
-- Database "egg_statistic" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2025-03-18 17:54:13

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4862 (class 1262 OID 32908)
-- Name: egg_statistic; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE egg_statistic WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';


ALTER DATABASE egg_statistic OWNER TO postgres;

\connect egg_statistic

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 219 (class 1259 OID 32990)
-- Name: app_settings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.app_settings (
    id uuid NOT NULL
);


ALTER TABLE public.app_settings OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 33107)
-- Name: chickens; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chickens (
    id uuid NOT NULL,
    chicken_amount character varying(255),
    day_statistic_id uuid NOT NULL
);


ALTER TABLE public.chickens OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 33079)
-- Name: chickens_amount; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chickens_amount (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    chicken_amount integer NOT NULL,
    is_active boolean NOT NULL,
    day_statistic_id uuid
);


ALTER TABLE public.chickens_amount OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 33018)
-- Name: chickens_settings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chickens_settings (
    id uuid NOT NULL,
    chicken_amount integer,
    app_setting_id uuid NOT NULL
);


ALTER TABLE public.chickens_settings OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 32919)
-- Name: day_statistic; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.day_statistic (
    amount integer,
    saved_at date,
    id uuid NOT NULL,
    feed_composition_id uuid,
    chickens_id uuid
);


ALTER TABLE public.day_statistic OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 32956)
-- Name: feed_component; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.feed_component (
    id uuid NOT NULL,
    feed_composition_id uuid NOT NULL,
    name character varying(255) NOT NULL,
    quantity character varying(255) NOT NULL
);


ALTER TABLE public.feed_component OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 32949)
-- Name: feed_composition; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.feed_composition (
    id uuid NOT NULL,
    name character varying(255) NOT NULL,
    date character varying(255) NOT NULL,
    is_active boolean
);


ALTER TABLE public.feed_composition OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 32968)
-- Name: vitamin; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vitamin (
    id uuid NOT NULL,
    feed_composition_id uuid NOT NULL,
    name character varying(255) NOT NULL,
    quantity character varying(255) NOT NULL
);


ALTER TABLE public.vitamin OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 33007)
-- Name: weather_forecast; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.weather_forecast (
    id uuid NOT NULL,
    day_statistic_id uuid NOT NULL,
    temperature double precision,
    humidity integer,
    wind_speed double precision,
    retrieved_successfully boolean DEFAULT false
);


ALTER TABLE public.weather_forecast OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 32995)
-- Name: weather_settings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.weather_settings (
    id uuid NOT NULL,
    weather_key character varying(255) NOT NULL,
    city character varying(255) NOT NULL,
    app_setting_id uuid NOT NULL
);


ALTER TABLE public.weather_settings OWNER TO postgres;

--
-- TOC entry 4851 (class 0 OID 32990)
-- Dependencies: 219
-- Data for Name: app_settings; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.app_settings (id) FROM stdin;
968b4c6b-ba60-4959-b1c5-28f8d11b35be
\.


--
-- TOC entry 4856 (class 0 OID 33107)
-- Dependencies: 224
-- Data for Name: chickens; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.chickens (id, chicken_amount, day_statistic_id) FROM stdin;
\.


--
-- TOC entry 4855 (class 0 OID 33079)
-- Dependencies: 223
-- Data for Name: chickens_amount; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.chickens_amount (id, chicken_amount, is_active, day_statistic_id) FROM stdin;
15db76af-b872-40d8-b61c-571840e79fc3	20	f	\N
\.


--
-- TOC entry 4854 (class 0 OID 33018)
-- Dependencies: 222
-- Data for Name: chickens_settings; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.chickens_settings (id, chicken_amount, app_setting_id) FROM stdin;
66c7e511-d79f-4171-8015-b2fa8ef75c7f	50	968b4c6b-ba60-4959-b1c5-28f8d11b35be
\.


--
-- TOC entry 4847 (class 0 OID 32919)
-- Dependencies: 215
-- Data for Name: day_statistic; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.day_statistic (amount, saved_at, id, feed_composition_id, chickens_id) FROM stdin;
6	2025-03-01	7e78b5ba-c9d0-470f-bd73-3a29692a38e1	4323388d-c11d-40e5-b653-51f9fd2b0080	15db76af-b872-40d8-b61c-571840e79fc3
10	2025-03-02	677b3a92-f2f1-4e42-b4e6-f9e2969a9fee	df771de6-af39-4ebc-ace1-26df325a0df7	15db76af-b872-40d8-b61c-571840e79fc3
9	2025-03-03	94cdcaf5-6fa3-4b4f-8ab3-8f61f6c70998	4323388d-c11d-40e5-b653-51f9fd2b0080	15db76af-b872-40d8-b61c-571840e79fc3
6	2025-03-04	f47bdb43-7db5-42fa-a9e8-c9d4af018903	1afeb832-8068-4b11-8252-b1d19611f3b5	15db76af-b872-40d8-b61c-571840e79fc3
6	2025-03-07	eda06b05-1001-48dd-bb60-88c5f3327d5a	1afeb832-8068-4b11-8252-b1d19611f3b5	15db76af-b872-40d8-b61c-571840e79fc3
7	2025-03-08	c342324f-2358-4035-a4f6-12ae400474ab	1afeb832-8068-4b11-8252-b1d19611f3b5	15db76af-b872-40d8-b61c-571840e79fc3
9	2025-03-05	195e12d4-fc76-4503-98ca-f6a6452f2755	1afeb832-8068-4b11-8252-b1d19611f3b5	15db76af-b872-40d8-b61c-571840e79fc3
10	2025-03-06	c0f10e95-2178-4e68-83d5-93707b18a670	1afeb832-8068-4b11-8252-b1d19611f3b5	15db76af-b872-40d8-b61c-571840e79fc3
5	2025-02-24	7fb29e87-582b-4e59-94e0-c3207cce6276	df771de6-af39-4ebc-ace1-26df325a0df7	15db76af-b872-40d8-b61c-571840e79fc3
11	2025-02-25	d7c49ac4-48f6-44d0-9122-59797b987c84	df771de6-af39-4ebc-ace1-26df325a0df7	15db76af-b872-40d8-b61c-571840e79fc3
13	2025-02-17	19c4e88e-a3ef-4a31-8b4e-7685344944dc	df771de6-af39-4ebc-ace1-26df325a0df7	15db76af-b872-40d8-b61c-571840e79fc3
10	2025-02-19	d9049b1f-a9db-4628-8da1-897b4ad7103f	df771de6-af39-4ebc-ace1-26df325a0df7	15db76af-b872-40d8-b61c-571840e79fc3
10	2025-02-20	6a9fc2ec-7984-4b37-9178-42db8cdc5c82	df771de6-af39-4ebc-ace1-26df325a0df7	15db76af-b872-40d8-b61c-571840e79fc3
14	2025-02-21	b93f6cd9-4073-4082-b46c-079c82d297ab	df771de6-af39-4ebc-ace1-26df325a0df7	15db76af-b872-40d8-b61c-571840e79fc3
6	2025-02-22	7ee05d8c-0ba4-40b3-a542-2a75c90889c5	df771de6-af39-4ebc-ace1-26df325a0df7	15db76af-b872-40d8-b61c-571840e79fc3
9	2025-02-23	218b77b1-4ec5-4bcd-83d6-a2db45d404a6	df771de6-af39-4ebc-ace1-26df325a0df7	15db76af-b872-40d8-b61c-571840e79fc3
11	2025-02-18	07c286a0-aea1-414d-8343-c9efa51f5b0f	df771de6-af39-4ebc-ace1-26df325a0df7	15db76af-b872-40d8-b61c-571840e79fc3
8	2025-02-26	4fa27507-cebf-4fb3-84dd-e5e6197aa972	df771de6-af39-4ebc-ace1-26df325a0df7	15db76af-b872-40d8-b61c-571840e79fc3
5	2025-02-27	f75906a0-6b06-4d7e-898f-eec576486659	df771de6-af39-4ebc-ace1-26df325a0df7	15db76af-b872-40d8-b61c-571840e79fc3
10	2025-02-28	2aac2c8d-bead-4975-b92f-2d59737772b0	df771de6-af39-4ebc-ace1-26df325a0df7	15db76af-b872-40d8-b61c-571840e79fc3
11	2025-03-09	3a0178ed-48fd-40c0-b015-3e1b84b7e8cd	1afeb832-8068-4b11-8252-b1d19611f3b5	\N
8	2025-03-10	ff2e2a74-3e31-4266-aeba-d3ceca687612	1afeb832-8068-4b11-8252-b1d19611f3b5	\N
13	2025-03-11	eac918f9-eed0-4365-9306-103845e2af6d	1afeb832-8068-4b11-8252-b1d19611f3b5	\N
8	2025-03-12	75386893-b93f-450a-83cb-433b120925ab	1afeb832-8068-4b11-8252-b1d19611f3b5	\N
14	2025-03-13	9f7d822e-c71d-4b44-bce7-4f7d4d1c80ee	1afeb832-8068-4b11-8252-b1d19611f3b5	\N
10	2025-03-14	505635ab-cf65-42a8-9277-b0d9e7452cb0	1afeb832-8068-4b11-8252-b1d19611f3b5	\N
13	2025-03-15	f8277e74-250d-424f-827a-1f8b3760049f	1afeb832-8068-4b11-8252-b1d19611f3b5	\N
10	2025-03-16	5a46cac6-1f10-43ea-9d32-dc176fdaf32d	1afeb832-8068-4b11-8252-b1d19611f3b5	\N
12	2025-03-17	adc8b8c9-8d7b-43ba-859f-fc47e04d36b9	1afeb832-8068-4b11-8252-b1d19611f3b5	\N
11	2025-03-18	61aa159c-b9be-4fe9-b9f6-1f4fd1068533	f0c9d410-358e-4e52-81f9-2b1fbac6fbd1	\N
\.


--
-- TOC entry 4849 (class 0 OID 32956)
-- Dependencies: 217
-- Data for Name: feed_component; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.feed_component (id, feed_composition_id, name, quantity) FROM stdin;
1a8f6ce0-f69b-4133-a30e-05fedcad0a80	4323388d-c11d-40e5-b653-51f9fd2b0080	2342	234234
ba2e79b7-bc98-4427-a64c-019580622908	df771de6-af39-4ebc-ace1-26df325a0df7	Пшениця	25
d169fcfd-c4d7-47b2-9b09-647cdfc9ccdc	df771de6-af39-4ebc-ace1-26df325a0df7	Кукурудза мелена	10
6a8a7c53-16bd-4610-93d6-1f19453a952b	df771de6-af39-4ebc-ace1-26df325a0df7	Жито Мелене	10
4b2025cb-8d51-4581-a739-cf43eaca7996	77f5342f-c475-44d8-a084-660de7c61347	Картопля	100
25521706-ec88-4773-b547-d590df00e425	1afeb832-8068-4b11-8252-b1d19611f3b5	Пшениця	10000
38eb0b0a-0efb-45a2-87d4-445876fb9a04	1afeb832-8068-4b11-8252-b1d19611f3b5	Макух	4000
6153dfd5-918a-461b-8c65-f9662eaa6665	1afeb832-8068-4b11-8252-b1d19611f3b5	Кукурудза	9000
c3ede5e9-d55f-44d9-86a7-26234be37c94	1afeb832-8068-4b11-8252-b1d19611f3b5	Жито 	7000
2eab0e49-ad09-40a4-b82a-17d3900a6c17	f0c9d410-358e-4e52-81f9-2b1fbac6fbd1	Пшениця	10000
ccd325fa-a65a-455e-ac42-39d3854104ab	f0c9d410-358e-4e52-81f9-2b1fbac6fbd1	Кукурудза	4000
31a28b30-5500-4cab-9d36-fe599614aab1	f0c9d410-358e-4e52-81f9-2b1fbac6fbd1	Жито 	2000
\.


--
-- TOC entry 4848 (class 0 OID 32949)
-- Dependencies: 216
-- Data for Name: feed_composition; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.feed_composition (id, name, date, is_active) FROM stdin;
4323388d-c11d-40e5-b653-51f9fd2b0080	Улюбленець курей	2025-02-25	f
77f5342f-c475-44d8-a084-660de7c61347	Варене	2025-03-03	f
df771de6-af39-4ebc-ace1-26df325a0df7	Кормова ракета	2025-02-25	f
1afeb832-8068-4b11-8252-b1d19611f3b5	Крупа, премікс, кальцій	2025-03-03	f
f0c9d410-358e-4e52-81f9-2b1fbac6fbd1	Дріжджі, премікс, м’ясокістна, премікс	2025-03-18	t
\.


--
-- TOC entry 4850 (class 0 OID 32968)
-- Dependencies: 218
-- Data for Name: vitamin; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vitamin (id, feed_composition_id, name, quantity) FROM stdin;
50d6df25-386f-4aca-a4ba-2944e084e09d	4323388d-c11d-40e5-b653-51f9fd2b0080	234234	23423
c791e1d6-a405-4084-a786-758d61b80d6e	df771de6-af39-4ebc-ace1-26df325a0df7	Премікс	25
ed806595-a3ae-47d6-a4d8-97cee391f48e	df771de6-af39-4ebc-ace1-26df325a0df7	Ракушка	10
32406264-b391-4975-a628-8c74a192464d	df771de6-af39-4ebc-ace1-26df325a0df7	Макух	200
fb653577-f03c-4718-8336-7a0d95e2021e	1afeb832-8068-4b11-8252-b1d19611f3b5	Премікс	300
6cce2662-ef2d-453e-8377-0ea8f88ffcce	1afeb832-8068-4b11-8252-b1d19611f3b5	Кальций	200
4cbcb058-f2c9-4c2b-8365-023ee00426dc	f0c9d410-358e-4e52-81f9-2b1fbac6fbd1	Дріжджі	500
0ffa0333-ad91-427c-a6e6-968ca317bcf3	f0c9d410-358e-4e52-81f9-2b1fbac6fbd1	М’ясокістна мука	500
07f87cc9-e622-4161-9911-bd078f029afd	f0c9d410-358e-4e52-81f9-2b1fbac6fbd1	Премікс	4
\.


--
-- TOC entry 4853 (class 0 OID 33007)
-- Dependencies: 221
-- Data for Name: weather_forecast; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.weather_forecast (id, day_statistic_id, temperature, humidity, wind_speed, retrieved_successfully) FROM stdin;
c59b3756-cb50-4614-af0e-a1693627cef4	195e12d4-fc76-4503-98ca-f6a6452f2755	9.79	43	5.5	t
2f52d276-714a-4c24-9d0e-69f10c55553b	c0f10e95-2178-4e68-83d5-93707b18a670	10.36	55	3.29	t
52a4eede-9dc3-42db-92ef-3582f7963eb2	eda06b05-1001-48dd-bb60-88c5f3327d5a	16.23	27	1.32	t
8958b20b-eac0-4cd7-8114-ef723f1cfce1	c342324f-2358-4035-a4f6-12ae400474ab	13.13	35	3.48	t
b6d1ce7a-3b24-44b4-b934-1ba7dd3e235d	3a0178ed-48fd-40c0-b015-3e1b84b7e8cd	9.44	50	4.03	t
afd3200e-3fd5-4762-8c32-71bfa6e4bfe6	ff2e2a74-3e31-4266-aeba-d3ceca687612	13.54	39	6.07	t
608c0d0f-ce2d-4c37-8ba2-8f1212de5c81	eac918f9-eed0-4365-9306-103845e2af6d	16.64	38	3.88	t
4c2cf74f-84fa-46d9-a681-6ed966ae552f	75386893-b93f-450a-83cb-433b120925ab	14.1	55	5.99	t
38c54df5-aa20-48b7-8a7e-06b3681cf61d	9f7d822e-c71d-4b44-bce7-4f7d4d1c80ee	16.18	61	8.26	t
6359bc28-b44d-47d2-8b25-9daff0b3b7ec	505635ab-cf65-42a8-9277-b0d9e7452cb0	14.19	53	2.49	t
310f4e67-9c4a-4730-9220-ffe806e81f56	f8277e74-250d-424f-827a-1f8b3760049f	1.73	93	5.83	t
e022a0ca-f66b-495b-b98c-946a8d893077	5a46cac6-1f10-43ea-9d32-dc176fdaf32d	5.1	60	2.72	t
1a8a94cc-5949-4b23-ba7e-0c01cf3d8657	adc8b8c9-8d7b-43ba-859f-fc47e04d36b9	1.03	63	8.4	t
3cca0415-bdae-4859-b591-aaad50c184e2	61aa159c-b9be-4fe9-b9f6-1f4fd1068533	2.22	30	7.17	t
\.


--
-- TOC entry 4852 (class 0 OID 32995)
-- Dependencies: 220
-- Data for Name: weather_settings; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.weather_settings (id, weather_key, city, app_setting_id) FROM stdin;
a38c8b0b-dc08-4127-8d2e-48b5f64ad12b	e624a47cf606c5a8344e0f674cc9f255	Khmelnytskyi	968b4c6b-ba60-4959-b1c5-28f8d11b35be
\.


--
-- TOC entry 4690 (class 2606 OID 33084)
-- Name: chickens_amount chickens_amount_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chickens_amount
    ADD CONSTRAINT chickens_amount_pkey PRIMARY KEY (id);


--
-- TOC entry 4692 (class 2606 OID 33111)
-- Name: chickens chickens_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chickens
    ADD CONSTRAINT chickens_pkey PRIMARY KEY (id);


--
-- TOC entry 4686 (class 2606 OID 33022)
-- Name: chickens_settings chickens_settings_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chickens_settings
    ADD CONSTRAINT chickens_settings_pkey PRIMARY KEY (id);


--
-- TOC entry 4672 (class 2606 OID 32923)
-- Name: day_statistic day_statistic_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.day_statistic
    ADD CONSTRAINT day_statistic_pkey PRIMARY KEY (id);


--
-- TOC entry 4676 (class 2606 OID 32962)
-- Name: feed_component feed_component_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.feed_component
    ADD CONSTRAINT feed_component_pkey PRIMARY KEY (id);


--
-- TOC entry 4674 (class 2606 OID 32955)
-- Name: feed_composition feed_composition_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.feed_composition
    ADD CONSTRAINT feed_composition_pkey PRIMARY KEY (id);


--
-- TOC entry 4680 (class 2606 OID 32994)
-- Name: app_settings settings_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.app_settings
    ADD CONSTRAINT settings_pkey PRIMARY KEY (id);


--
-- TOC entry 4688 (class 2606 OID 33024)
-- Name: chickens_settings uk1lgqejp63tqy106oay3ysebx8; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chickens_settings
    ADD CONSTRAINT uk1lgqejp63tqy106oay3ysebx8 UNIQUE (app_setting_id);


--
-- TOC entry 4694 (class 2606 OID 33113)
-- Name: chickens ukqx1o4mpcot9nxquj7ihkai9rn; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chickens
    ADD CONSTRAINT ukqx1o4mpcot9nxquj7ihkai9rn UNIQUE (day_statistic_id);


--
-- TOC entry 4678 (class 2606 OID 32974)
-- Name: vitamin vitamin_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vitamin
    ADD CONSTRAINT vitamin_pkey PRIMARY KEY (id);


--
-- TOC entry 4684 (class 2606 OID 33012)
-- Name: weather_forecast weather_forecast_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.weather_forecast
    ADD CONSTRAINT weather_forecast_pkey PRIMARY KEY (id);


--
-- TOC entry 4682 (class 2606 OID 32999)
-- Name: weather_settings weather_settings_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.weather_settings
    ADD CONSTRAINT weather_settings_pkey PRIMARY KEY (id);


--
-- TOC entry 4695 (class 2606 OID 32985)
-- Name: day_statistic day_component_feed_composition_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.day_statistic
    ADD CONSTRAINT day_component_feed_composition_id_fkey FOREIGN KEY (feed_composition_id) REFERENCES public.feed_composition(id) ON DELETE CASCADE;


--
-- TOC entry 4697 (class 2606 OID 32963)
-- Name: feed_component feed_component_feed_composition_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.feed_component
    ADD CONSTRAINT feed_component_feed_composition_id_fkey FOREIGN KEY (feed_composition_id) REFERENCES public.feed_composition(id) ON DELETE CASCADE;


--
-- TOC entry 4701 (class 2606 OID 33025)
-- Name: chickens_settings fk2tng4vqdwvf7hphqwx87oquhu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chickens_settings
    ADD CONSTRAINT fk2tng4vqdwvf7hphqwx87oquhu FOREIGN KEY (app_setting_id) REFERENCES public.app_settings(id);


--
-- TOC entry 4696 (class 2606 OID 33085)
-- Name: day_statistic fk_chickens; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.day_statistic
    ADD CONSTRAINT fk_chickens FOREIGN KEY (chickens_id) REFERENCES public.chickens_amount(id) ON DELETE CASCADE;


--
-- TOC entry 4700 (class 2606 OID 33013)
-- Name: weather_forecast fk_day_statistic; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.weather_forecast
    ADD CONSTRAINT fk_day_statistic FOREIGN KEY (day_statistic_id) REFERENCES public.day_statistic(id) ON DELETE CASCADE;


--
-- TOC entry 4702 (class 2606 OID 33123)
-- Name: chickens_amount fklg1j26xoivnq8pni1dr5ddtn8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chickens_amount
    ADD CONSTRAINT fklg1j26xoivnq8pni1dr5ddtn8 FOREIGN KEY (day_statistic_id) REFERENCES public.day_statistic(id);


--
-- TOC entry 4703 (class 2606 OID 33114)
-- Name: chickens fksli1jjlehqa2loyyhh9h96xly; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chickens
    ADD CONSTRAINT fksli1jjlehqa2loyyhh9h96xly FOREIGN KEY (day_statistic_id) REFERENCES public.day_statistic(id);


--
-- TOC entry 4698 (class 2606 OID 32975)
-- Name: vitamin vitamin_feed_composition_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vitamin
    ADD CONSTRAINT vitamin_feed_composition_id_fkey FOREIGN KEY (feed_composition_id) REFERENCES public.feed_composition(id) ON DELETE CASCADE;


--
-- TOC entry 4699 (class 2606 OID 33000)
-- Name: weather_settings weather_settings_app_setting_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.weather_settings
    ADD CONSTRAINT weather_settings_app_setting_id_fkey FOREIGN KEY (app_setting_id) REFERENCES public.app_settings(id) ON DELETE CASCADE;


-- Completed on 2025-03-18 17:54:14

--
-- PostgreSQL database dump complete
--

--
-- Database "gym" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2025-03-18 17:54:14

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4867 (class 1262 OID 16398)
-- Name: gym; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE gym WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';


ALTER DATABASE gym OWNER TO postgres;

\connect gym

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2 (class 3079 OID 16444)
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- TOC entry 4868 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 219 (class 1259 OID 16419)
-- Name: chosen_subscription; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chosen_subscription (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    sub_type uuid,
    price money,
    coach boolean,
    is_active boolean DEFAULT false,
    activation_date date,
    sub_id uuid
);


ALTER TABLE public.chosen_subscription OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16414)
-- Name: coach; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.coach (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    name character varying(50),
    last_name character varying(50),
    price money,
    busy_date_time date
);


ALTER TABLE public.coach OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 24701)
-- Name: gym_subscriptions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.gym_subscriptions (
    id uuid DEFAULT public.uuid_generate_v1() NOT NULL,
    sub_type character varying NOT NULL,
    duration_days integer,
    price double precision
);


ALTER TABLE public.gym_subscriptions OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16470)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id uuid NOT NULL,
    name character varying
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 24654)
-- Name: staff_users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.staff_users (
    id uuid DEFAULT public.uuid_generate_v1() NOT NULL,
    user_name character varying,
    password character varying,
    enabled boolean
);


ALTER TABLE public.staff_users OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16404)
-- Name: user_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_history (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    sub_type character varying(50),
    price money,
    user_id uuid,
    end_sub_date date,
    start_sub_date date
);


ALTER TABLE public.user_history OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 24672)
-- Name: user_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_roles (
    user_id uuid NOT NULL,
    role_id uuid NOT NULL
);


ALTER TABLE public.user_roles OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16424)
-- Name: user_subscription; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_subscription (
    user_id uuid NOT NULL,
    sub_id uuid NOT NULL
);


ALTER TABLE public.user_subscription OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16399)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    name character varying(128),
    last_name character varying(128),
    reg_date date,
    email character varying,
    phone_number character varying,
    social_media character varying
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 4856 (class 0 OID 16419)
-- Dependencies: 219
-- Data for Name: chosen_subscription; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.chosen_subscription (id, sub_type, price, coach, is_active, activation_date, sub_id) FROM stdin;
5fea638c-dee2-40c1-8ca1-938d28553617	e81edd2c-8159-11ef-8c7c-bfd1e29130da	$50.00	t	t	2024-03-12	9caaae3c-8098-11ef-8f12-e3c24c07a2b8
efffb3b6-d29d-4041-b008-a097320a56e9	9caaae3c-8098-11ef-8f12-e3c24c07a2b8	$50.00	f	t	2024-01-02	9caaae3c-8098-11ef-8f12-e3c24c07a2b8
c9650efe-b35f-490b-9e63-9014c8713010	9caaae3c-8098-11ef-8f12-e3c24c07a2b8	$30.00	f	t	2024-05-08	9caaae3c-8098-11ef-8f12-e3c24c07a2b8
4c3f5262-acc4-4c2c-b5ee-12b06ab3e5d5	e8214e2c-8159-11ef-8c7f-93cae9721fc7	$20.00	f	t	2024-08-23	9caaae3c-8098-11ef-8f12-e3c24c07a2b8
939d6508-2411-4881-9975-9555c89d32e9	e82015ac-8159-11ef-8c7d-c3d924d2bd74	$30.00	f	t	2024-02-14	9caaae3c-8098-11ef-8f12-e3c24c07a2b8
227a8a38-2330-44fe-b196-ad80f9bf28ac	e820b1e2-8159-11ef-8c7e-e30c785af28e	$45.00	f	t	2024-07-15	9caaae3c-8098-11ef-8f12-e3c24c07a2b8
4f9c21b4-52c7-4cd5-acb9-f2f9c31c9a7c	e82015ac-8159-11ef-8c7d-c3d924d2bd74	$40.00	t	t	2024-06-27	9caaae3c-8098-11ef-8f12-e3c24c07a2b8
2fe1d425-0748-4e6d-8e4d-ac11cc4cba46	e8214e2c-8159-11ef-8c7f-93cae9721fc7	$15.00	t	t	2024-01-25	9caaae3c-8098-11ef-8f12-e3c24c07a2b8
\.


--
-- TOC entry 4855 (class 0 OID 16414)
-- Dependencies: 218
-- Data for Name: coach; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.coach (id, name, last_name, price, busy_date_time) FROM stdin;
b2f669f6-fbdc-4c9e-b0f7-2f6229b23d8c	Jimmy	Johnson	$150.00	2023-02-01
15ec1836-4f48-4cfa-8fc6-0b85e6dc60de	Willie	Jones	$300.00	2023-05-01
61b27629-b7e9-409d-b1b5-0063a532c91c	Mike	Brown	$250.00	2023-04-01
e8bff9cd-5c05-4598-b26d-05b03ccd1836	Robie	Williams	$200.00	2023-03-01
de1df31e-9f59-4ea1-9db4-1bf504fce5b6	John	Smith	$100.00	2023-01-01
\.


--
-- TOC entry 4861 (class 0 OID 24701)
-- Dependencies: 224
-- Data for Name: gym_subscriptions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.gym_subscriptions (id, sub_type, duration_days, price) FROM stdin;
e82015ac-8159-11ef-8c7d-c3d924d2bd74	YEARLY	365	200
e81edd2c-8159-11ef-8c7c-bfd1e29130da	MONTHLY	30	55
e820b1e2-8159-11ef-8c7e-e30c785af28e	WEEKLY	7	25
9caaae3c-8098-11ef-8f12-e3c24c07a2b8	VIP	365	300
e8214e2c-8159-11ef-8c7f-93cae9721fc7	ONE_DAY	1	3
\.


--
-- TOC entry 4858 (class 0 OID 16470)
-- Dependencies: 221
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, name) FROM stdin;
613c4c7d-c476-4da5-8151-5730e55a11a7	STAFF
39317991-79c4-42a3-90f2-751f34c322e5	ADMIN
fefab2f1-f82a-4157-8335-a9372d78a3fc	USER
\.


--
-- TOC entry 4859 (class 0 OID 24654)
-- Dependencies: 222
-- Data for Name: staff_users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.staff_users (id, user_name, password, enabled) FROM stdin;
15cb0f9f-f573-49cd-930b-e8718780df77	testuser@gmail.com	$2a$10$YSUo/BgrpcXE6K/yRT0oM.59Z8xWaV0Oo6vocuVzBeNOemLh9dEqe	t
f0a7674c-d14a-404f-8741-f5e174ee771d	admin@gmail.com	$2a$10$GaQS.CHOgaq53VlXBsKyZecDXXgnf6inaZ0Dui5RcdIFaS4XkiCaC	t
864ef3a9-0eb1-467a-8462-67005106f342	user1@gmail.com	$2a$10$FiZVIQ1GN7/JFzzPT7LXBepdd0pSjl5bo/fGaZ0rF2aNEWsgrSNVK	t
\.


--
-- TOC entry 4854 (class 0 OID 16404)
-- Dependencies: 217
-- Data for Name: user_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_history (id, sub_type, price, user_id, end_sub_date, start_sub_date) FROM stdin;
bcd4efd9-51ef-4f2b-92b2-4509c925f082	Basic	$20.00	a90c84c9-5b44-4c0b-bdb6-4a2f772b9b81	2023-12-31	2023-01-01
cdbacb9e-8716-4420-a6f1-bfb22ac778be	Standard	$30.00	e35151ba-1c23-4561-ac60-bc01a98a1cb9	2023-12-31	2023-02-01
bc19f6de-549e-41b1-8308-7d35ffec7de1	Premium	$40.00	aa3ea642-7195-417f-9824-6d64aabc525b	2023-12-31	2023-03-01
5622effe-8bd6-4988-9c48-d318e1d71c5e	VIP	$50.00	0b0b147e-7860-4109-b789-0244d41173f4	2023-12-31	2023-04-01
01ca411a-d896-4208-965d-18f3bc3ef5a1	Basic	$20.00	6e7365e3-bf4d-4472-ab4d-49b7e61b052a	2023-12-31	2023-05-01
\.


--
-- TOC entry 4860 (class 0 OID 24672)
-- Dependencies: 223
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_roles (user_id, role_id) FROM stdin;
15cb0f9f-f573-49cd-930b-e8718780df77	613c4c7d-c476-4da5-8151-5730e55a11a7
f0a7674c-d14a-404f-8741-f5e174ee771d	39317991-79c4-42a3-90f2-751f34c322e5
864ef3a9-0eb1-467a-8462-67005106f342	fefab2f1-f82a-4157-8335-a9372d78a3fc
\.


--
-- TOC entry 4857 (class 0 OID 16424)
-- Dependencies: 220
-- Data for Name: user_subscription; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_subscription (user_id, sub_id) FROM stdin;
88423f99-ddf1-404b-b3ac-e2a79b58d0c7	4c3f5262-acc4-4c2c-b5ee-12b06ab3e5d5
aa3ea642-7195-417f-9824-6d64aabc525b	4f9c21b4-52c7-4cd5-acb9-f2f9c31c9a7c
e35151ba-1c23-4561-ac60-bc01a98a1cb9	c9650efe-b35f-490b-9e63-9014c8713010
a90c84c9-5b44-4c0b-bdb6-4a2f772b9b81	4c3f5262-acc4-4c2c-b5ee-12b06ab3e5d5
0b0b147e-7860-4109-b789-0244d41173f4	5fea638c-dee2-40c1-8ca1-938d28553617
6e7365e3-bf4d-4472-ab4d-49b7e61b052a	4c3f5262-acc4-4c2c-b5ee-12b06ab3e5d5
\.


--
-- TOC entry 4853 (class 0 OID 16399)
-- Dependencies: 216
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, name, last_name, reg_date, email, phone_number, social_media) FROM stdin;
88423f99-ddf1-404b-b3ac-e2a79b58d0c7	Emma	Garcia	2023-07-01	\N	\N	\N
9604ca9d-4544-4fc0-8f2f-01c528823fb8	George	Bush	2024-07-09	\N	\N	\N
63b7f1f8-e293-47f0-825c-d63d16f3c2c2	Quinn	Lopez	2024-07-01	\N	\N	\N
c421a821-431e-4dcc-9069-128d3dafbdd0	Mia	Martin	2024-03-01	\N	\N	\N
d55ce26e-fbdb-417c-ba9e-6d7c4ea7f960	Kathy	Hernandez	2024-01-01	\N	\N	\N
a90c84c9-5b44-4c0b-bdb6-4a2f772b9b81	John	Doe	2023-01-01	\N	\N	\N
23e68c56-de74-49ef-92ad-99174ee701ca	Hank	Taylor	2023-10-01	\N	\N	\N
282d1315-9f56-4dbf-8eda-41888397dea9	Grace	Wilson	2023-09-01	\N	\N	\N
4aadc463-4a71-408f-8b4a-2143fa3ad69b	Leo	Moore	2024-02-01	\N	\N	\N
66324b23-6590-401e-9df7-70b86175fdb6	Paul	White	2024-06-01	\N	\N	\N
aa87f1a8-a59b-48b3-9fae-9bf6deadf9a7	Nick	Jackson	2024-04-01	\N	\N	\N
a5ab24cf-37d3-4480-bfff-bf7d03fcdc95	Frank	Martinez	2023-08-01	\N	\N	\N
0c1fe7af-116b-4d04-803c-3c29bce62394	Olivia	Thompson	2024-05-01	\N	\N	\N
aad99f89-79db-4c7b-8eaf-5a1ac83dbc0b	Jack	Thomas	2023-12-01	\N	\N	\N
01b0cc29-1b3b-4d0d-962e-bdc755f2033e	David	Jones	2023-06-01	\N	\N	\N
6e7365e3-bf4d-4472-ab4d-49b7e61b052a	Carol	Brown	2023-05-01	\N	\N	\N
0b0b147e-7860-4109-b789-0244d41173f4	Bob	Williams	2023-04-01	\N	\N	\N
cd2da911-a77a-4de5-bfe0-68da6ad88b01	Violetta	Bruno	2024-09-17	Violetta@mail.com	098552147	facebook.com
578ecef0-143f-4904-b209-0856182afadd	Rick	Clark	2024-08-01	\N	\N	\N
eaf17918-7403-4e15-bc4e-4d3ad6c712a0	Ivy	Anderson	2023-11-01	\N	\N	\N
aa3ea642-7195-417f-9824-6d64aabc525b	Alice	Johnson	2023-03-01	\N	\N	\N
e35151ba-1c23-4561-ac60-bc01a98a1cb9	Jane	Smith	2023-02-01	\N	\N	\N
4168fa7e-fb43-4d15-89cc-1790904b9118	\N	\N	\N	\N	\N	\N
7b09f663-6636-49c8-8d92-6c3367f8d189	\N	\N	\N	\N	\N	\N
88717095-dba0-4cb5-99bc-21c9a9e2e140	\N	\N	\N	\N	\N	\N
eecff293-b4dd-48ba-b885-ac2e6cb317b7	\N	\N	\N	\N	\N	\N
6771296e-6b1e-48d2-bd0e-582335b982e6	\N	\N	\N	\N	\N	\N
\.


--
-- TOC entry 4688 (class 2606 OID 16418)
-- Name: coach coach_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.coach
    ADD CONSTRAINT coach_pkey PRIMARY KEY (id);


--
-- TOC entry 4700 (class 2606 OID 24708)
-- Name: gym_subscriptions gym_subscriptions_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gym_subscriptions
    ADD CONSTRAINT gym_subscriptions_pk PRIMARY KEY (id);


--
-- TOC entry 4702 (class 2606 OID 24710)
-- Name: gym_subscriptions gym_subscriptions_pk_2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gym_subscriptions
    ADD CONSTRAINT gym_subscriptions_pk_2 UNIQUE (sub_type);


--
-- TOC entry 4694 (class 2606 OID 16477)
-- Name: roles roles_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pk PRIMARY KEY (id);


--
-- TOC entry 4696 (class 2606 OID 24671)
-- Name: staff_users staff_users_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.staff_users
    ADD CONSTRAINT staff_users_pk PRIMARY KEY (id);


--
-- TOC entry 4690 (class 2606 OID 16423)
-- Name: chosen_subscription subscriptions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chosen_subscription
    ADD CONSTRAINT subscriptions_pkey PRIMARY KEY (id);


--
-- TOC entry 4686 (class 2606 OID 16408)
-- Name: user_history user_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_history
    ADD CONSTRAINT user_history_pkey PRIMARY KEY (id);


--
-- TOC entry 4698 (class 2606 OID 24676)
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 4692 (class 2606 OID 24698)
-- Name: user_subscription user_subscription_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_subscription
    ADD CONSTRAINT user_subscription_pk PRIMARY KEY (user_id, sub_id);


--
-- TOC entry 4684 (class 2606 OID 16403)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4704 (class 2606 OID 24711)
-- Name: chosen_subscription subscriptions_gym_subscriptions_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chosen_subscription
    ADD CONSTRAINT subscriptions_gym_subscriptions_id_fk FOREIGN KEY (sub_id) REFERENCES public.gym_subscriptions(id);


--
-- TOC entry 4705 (class 2606 OID 24732)
-- Name: chosen_subscription subscriptions_gym_subscriptions_id_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chosen_subscription
    ADD CONSTRAINT subscriptions_gym_subscriptions_id_fk_2 FOREIGN KEY (sub_type) REFERENCES public.gym_subscriptions(id);


--
-- TOC entry 4703 (class 2606 OID 16409)
-- Name: user_history user_history_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_history
    ADD CONSTRAINT user_history_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4708 (class 2606 OID 24682)
-- Name: user_roles user_roles_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id) ON DELETE CASCADE;


--
-- TOC entry 4709 (class 2606 OID 24677)
-- Name: user_roles user_roles_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.staff_users(id) ON DELETE CASCADE;


--
-- TOC entry 4706 (class 2606 OID 16429)
-- Name: user_subscription user_subscription_sub_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_subscription
    ADD CONSTRAINT user_subscription_sub_id_fkey FOREIGN KEY (sub_id) REFERENCES public.chosen_subscription(id);


--
-- TOC entry 4707 (class 2606 OID 16434)
-- Name: user_subscription user_subscription_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_subscription
    ADD CONSTRAINT user_subscription_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


-- Completed on 2025-03-18 17:54:14

--
-- PostgreSQL database dump complete
--

--
-- Database "postgres" dump
--

\connect postgres

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2025-03-18 17:54:14

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2 (class 3079 OID 16384)
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- TOC entry 4778 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


-- Completed on 2025-03-18 17:54:14

--
-- PostgreSQL database dump complete
--

-- Completed on 2025-03-18 17:54:14

--
-- PostgreSQL database cluster dump complete
--

