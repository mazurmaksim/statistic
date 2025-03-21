--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2025-03-21 17:28:15

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'WIN1251';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 4862 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


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

INSERT INTO public.app_settings VALUES ('968b4c6b-ba60-4959-b1c5-28f8d11b35be');


--
-- TOC entry 4856 (class 0 OID 33107)
-- Dependencies: 224
-- Data for Name: chickens; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4855 (class 0 OID 33079)
-- Dependencies: 223
-- Data for Name: chickens_amount; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.chickens_amount VALUES ('15db76af-b872-40d8-b61c-571840e79fc3', 20, false, NULL);


--
-- TOC entry 4854 (class 0 OID 33018)
-- Dependencies: 222
-- Data for Name: chickens_settings; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.chickens_settings VALUES ('66c7e511-d79f-4171-8015-b2fa8ef75c7f', 50, '968b4c6b-ba60-4959-b1c5-28f8d11b35be');


--
-- TOC entry 4847 (class 0 OID 32919)
-- Dependencies: 215
-- Data for Name: day_statistic; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.day_statistic VALUES (12, '2025-03-18', '61aa159c-b9be-4fe9-b9f6-1f4fd1068533', 'f0c9d410-358e-4e52-81f9-2b1fbac6fbd1', NULL);
INSERT INTO public.day_statistic VALUES (13, '2025-03-19', '460e808f-bda8-4d9a-8a00-c21dbf6d36b1', 'f0c9d410-358e-4e52-81f9-2b1fbac6fbd1', NULL);
INSERT INTO public.day_statistic VALUES (9, '2025-03-20', 'd34a4042-7476-45bd-a0e1-5181934181b9', 'f0c9d410-358e-4e52-81f9-2b1fbac6fbd1', NULL);
INSERT INTO public.day_statistic VALUES (11, '2025-03-21', 'f1392254-a6ac-496b-8730-283416df9f8c', 'f0c9d410-358e-4e52-81f9-2b1fbac6fbd1', NULL);
INSERT INTO public.day_statistic VALUES (6, '2025-03-01', '7e78b5ba-c9d0-470f-bd73-3a29692a38e1', '4323388d-c11d-40e5-b653-51f9fd2b0080', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (10, '2025-03-02', '677b3a92-f2f1-4e42-b4e6-f9e2969a9fee', 'df771de6-af39-4ebc-ace1-26df325a0df7', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (9, '2025-03-03', '94cdcaf5-6fa3-4b4f-8ab3-8f61f6c70998', '4323388d-c11d-40e5-b653-51f9fd2b0080', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (6, '2025-03-04', 'f47bdb43-7db5-42fa-a9e8-c9d4af018903', '1afeb832-8068-4b11-8252-b1d19611f3b5', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (6, '2025-03-07', 'eda06b05-1001-48dd-bb60-88c5f3327d5a', '1afeb832-8068-4b11-8252-b1d19611f3b5', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (7, '2025-03-08', 'c342324f-2358-4035-a4f6-12ae400474ab', '1afeb832-8068-4b11-8252-b1d19611f3b5', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (9, '2025-03-05', '195e12d4-fc76-4503-98ca-f6a6452f2755', '1afeb832-8068-4b11-8252-b1d19611f3b5', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (10, '2025-03-06', 'c0f10e95-2178-4e68-83d5-93707b18a670', '1afeb832-8068-4b11-8252-b1d19611f3b5', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (5, '2025-02-24', '7fb29e87-582b-4e59-94e0-c3207cce6276', 'df771de6-af39-4ebc-ace1-26df325a0df7', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (11, '2025-02-25', 'd7c49ac4-48f6-44d0-9122-59797b987c84', 'df771de6-af39-4ebc-ace1-26df325a0df7', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (13, '2025-02-17', '19c4e88e-a3ef-4a31-8b4e-7685344944dc', 'df771de6-af39-4ebc-ace1-26df325a0df7', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (10, '2025-02-19', 'd9049b1f-a9db-4628-8da1-897b4ad7103f', 'df771de6-af39-4ebc-ace1-26df325a0df7', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (10, '2025-02-20', '6a9fc2ec-7984-4b37-9178-42db8cdc5c82', 'df771de6-af39-4ebc-ace1-26df325a0df7', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (14, '2025-02-21', 'b93f6cd9-4073-4082-b46c-079c82d297ab', 'df771de6-af39-4ebc-ace1-26df325a0df7', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (6, '2025-02-22', '7ee05d8c-0ba4-40b3-a542-2a75c90889c5', 'df771de6-af39-4ebc-ace1-26df325a0df7', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (9, '2025-02-23', '218b77b1-4ec5-4bcd-83d6-a2db45d404a6', 'df771de6-af39-4ebc-ace1-26df325a0df7', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (11, '2025-02-18', '07c286a0-aea1-414d-8343-c9efa51f5b0f', 'df771de6-af39-4ebc-ace1-26df325a0df7', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (8, '2025-02-26', '4fa27507-cebf-4fb3-84dd-e5e6197aa972', 'df771de6-af39-4ebc-ace1-26df325a0df7', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (5, '2025-02-27', 'f75906a0-6b06-4d7e-898f-eec576486659', 'df771de6-af39-4ebc-ace1-26df325a0df7', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (10, '2025-02-28', '2aac2c8d-bead-4975-b92f-2d59737772b0', 'df771de6-af39-4ebc-ace1-26df325a0df7', '15db76af-b872-40d8-b61c-571840e79fc3');
INSERT INTO public.day_statistic VALUES (11, '2025-03-09', '3a0178ed-48fd-40c0-b015-3e1b84b7e8cd', '1afeb832-8068-4b11-8252-b1d19611f3b5', NULL);
INSERT INTO public.day_statistic VALUES (8, '2025-03-10', 'ff2e2a74-3e31-4266-aeba-d3ceca687612', '1afeb832-8068-4b11-8252-b1d19611f3b5', NULL);
INSERT INTO public.day_statistic VALUES (13, '2025-03-11', 'eac918f9-eed0-4365-9306-103845e2af6d', '1afeb832-8068-4b11-8252-b1d19611f3b5', NULL);
INSERT INTO public.day_statistic VALUES (8, '2025-03-12', '75386893-b93f-450a-83cb-433b120925ab', '1afeb832-8068-4b11-8252-b1d19611f3b5', NULL);
INSERT INTO public.day_statistic VALUES (14, '2025-03-13', '9f7d822e-c71d-4b44-bce7-4f7d4d1c80ee', '1afeb832-8068-4b11-8252-b1d19611f3b5', NULL);
INSERT INTO public.day_statistic VALUES (10, '2025-03-14', '505635ab-cf65-42a8-9277-b0d9e7452cb0', '1afeb832-8068-4b11-8252-b1d19611f3b5', NULL);
INSERT INTO public.day_statistic VALUES (13, '2025-03-15', 'f8277e74-250d-424f-827a-1f8b3760049f', '1afeb832-8068-4b11-8252-b1d19611f3b5', NULL);
INSERT INTO public.day_statistic VALUES (10, '2025-03-16', '5a46cac6-1f10-43ea-9d32-dc176fdaf32d', '1afeb832-8068-4b11-8252-b1d19611f3b5', NULL);
INSERT INTO public.day_statistic VALUES (12, '2025-03-17', 'adc8b8c9-8d7b-43ba-859f-fc47e04d36b9', '1afeb832-8068-4b11-8252-b1d19611f3b5', NULL);


--
-- TOC entry 4849 (class 0 OID 32956)
-- Dependencies: 217
-- Data for Name: feed_component; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.feed_component VALUES ('1a8f6ce0-f69b-4133-a30e-05fedcad0a80', '4323388d-c11d-40e5-b653-51f9fd2b0080', '2342', '234234');
INSERT INTO public.feed_component VALUES ('ba2e79b7-bc98-4427-a64c-019580622908', 'df771de6-af39-4ebc-ace1-26df325a0df7', 'Пшениця', '25');
INSERT INTO public.feed_component VALUES ('d169fcfd-c4d7-47b2-9b09-647cdfc9ccdc', 'df771de6-af39-4ebc-ace1-26df325a0df7', 'Кукурудза мелена', '10');
INSERT INTO public.feed_component VALUES ('6a8a7c53-16bd-4610-93d6-1f19453a952b', 'df771de6-af39-4ebc-ace1-26df325a0df7', 'Жито Мелене', '10');
INSERT INTO public.feed_component VALUES ('4b2025cb-8d51-4581-a739-cf43eaca7996', '77f5342f-c475-44d8-a084-660de7c61347', 'Картопля', '100');
INSERT INTO public.feed_component VALUES ('25521706-ec88-4773-b547-d590df00e425', '1afeb832-8068-4b11-8252-b1d19611f3b5', 'Пшениця', '10000');
INSERT INTO public.feed_component VALUES ('38eb0b0a-0efb-45a2-87d4-445876fb9a04', '1afeb832-8068-4b11-8252-b1d19611f3b5', 'Макух', '4000');
INSERT INTO public.feed_component VALUES ('6153dfd5-918a-461b-8c65-f9662eaa6665', '1afeb832-8068-4b11-8252-b1d19611f3b5', 'Кукурудза', '9000');
INSERT INTO public.feed_component VALUES ('c3ede5e9-d55f-44d9-86a7-26234be37c94', '1afeb832-8068-4b11-8252-b1d19611f3b5', 'Жито ', '7000');
INSERT INTO public.feed_component VALUES ('2eab0e49-ad09-40a4-b82a-17d3900a6c17', 'f0c9d410-358e-4e52-81f9-2b1fbac6fbd1', 'Пшениця', '10000');
INSERT INTO public.feed_component VALUES ('ccd325fa-a65a-455e-ac42-39d3854104ab', 'f0c9d410-358e-4e52-81f9-2b1fbac6fbd1', 'Кукурудза', '4000');
INSERT INTO public.feed_component VALUES ('31a28b30-5500-4cab-9d36-fe599614aab1', 'f0c9d410-358e-4e52-81f9-2b1fbac6fbd1', 'Жито ', '2000');


--
-- TOC entry 4848 (class 0 OID 32949)
-- Dependencies: 216
-- Data for Name: feed_composition; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.feed_composition VALUES ('4323388d-c11d-40e5-b653-51f9fd2b0080', 'Улюбленець курей', '2025-02-25', false);
INSERT INTO public.feed_composition VALUES ('77f5342f-c475-44d8-a084-660de7c61347', 'Варене', '2025-03-03', false);
INSERT INTO public.feed_composition VALUES ('df771de6-af39-4ebc-ace1-26df325a0df7', 'Кормова ракета', '2025-02-25', false);
INSERT INTO public.feed_composition VALUES ('1afeb832-8068-4b11-8252-b1d19611f3b5', 'Крупа, премікс, кальцій', '2025-03-03', false);
INSERT INTO public.feed_composition VALUES ('f0c9d410-358e-4e52-81f9-2b1fbac6fbd1', 'Дріжджі, премікс, м’ясокістна мука', '2025-03-18', true);


--
-- TOC entry 4850 (class 0 OID 32968)
-- Dependencies: 218
-- Data for Name: vitamin; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.vitamin VALUES ('50d6df25-386f-4aca-a4ba-2944e084e09d', '4323388d-c11d-40e5-b653-51f9fd2b0080', '234234', '23423');
INSERT INTO public.vitamin VALUES ('c791e1d6-a405-4084-a786-758d61b80d6e', 'df771de6-af39-4ebc-ace1-26df325a0df7', 'Премікс', '25');
INSERT INTO public.vitamin VALUES ('ed806595-a3ae-47d6-a4d8-97cee391f48e', 'df771de6-af39-4ebc-ace1-26df325a0df7', 'Ракушка', '10');
INSERT INTO public.vitamin VALUES ('32406264-b391-4975-a628-8c74a192464d', 'df771de6-af39-4ebc-ace1-26df325a0df7', 'Макух', '200');
INSERT INTO public.vitamin VALUES ('fb653577-f03c-4718-8336-7a0d95e2021e', '1afeb832-8068-4b11-8252-b1d19611f3b5', 'Премікс', '300');
INSERT INTO public.vitamin VALUES ('6cce2662-ef2d-453e-8377-0ea8f88ffcce', '1afeb832-8068-4b11-8252-b1d19611f3b5', 'Кальций', '200');
INSERT INTO public.vitamin VALUES ('4cbcb058-f2c9-4c2b-8365-023ee00426dc', 'f0c9d410-358e-4e52-81f9-2b1fbac6fbd1', 'Дріжджі', '500');
INSERT INTO public.vitamin VALUES ('0ffa0333-ad91-427c-a6e6-968ca317bcf3', 'f0c9d410-358e-4e52-81f9-2b1fbac6fbd1', 'М’ясокістна мука', '500');
INSERT INTO public.vitamin VALUES ('07f87cc9-e622-4161-9911-bd078f029afd', 'f0c9d410-358e-4e52-81f9-2b1fbac6fbd1', 'Премікс', '4');


--
-- TOC entry 4853 (class 0 OID 33007)
-- Dependencies: 221
-- Data for Name: weather_forecast; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.weather_forecast VALUES ('c59b3756-cb50-4614-af0e-a1693627cef4', '195e12d4-fc76-4503-98ca-f6a6452f2755', 9.79, 43, 5.5, true);
INSERT INTO public.weather_forecast VALUES ('2f52d276-714a-4c24-9d0e-69f10c55553b', 'c0f10e95-2178-4e68-83d5-93707b18a670', 10.36, 55, 3.29, true);
INSERT INTO public.weather_forecast VALUES ('52a4eede-9dc3-42db-92ef-3582f7963eb2', 'eda06b05-1001-48dd-bb60-88c5f3327d5a', 16.23, 27, 1.32, true);
INSERT INTO public.weather_forecast VALUES ('8958b20b-eac0-4cd7-8114-ef723f1cfce1', 'c342324f-2358-4035-a4f6-12ae400474ab', 13.13, 35, 3.48, true);
INSERT INTO public.weather_forecast VALUES ('b6d1ce7a-3b24-44b4-b934-1ba7dd3e235d', '3a0178ed-48fd-40c0-b015-3e1b84b7e8cd', 9.44, 50, 4.03, true);
INSERT INTO public.weather_forecast VALUES ('afd3200e-3fd5-4762-8c32-71bfa6e4bfe6', 'ff2e2a74-3e31-4266-aeba-d3ceca687612', 13.54, 39, 6.07, true);
INSERT INTO public.weather_forecast VALUES ('608c0d0f-ce2d-4c37-8ba2-8f1212de5c81', 'eac918f9-eed0-4365-9306-103845e2af6d', 16.64, 38, 3.88, true);
INSERT INTO public.weather_forecast VALUES ('4c2cf74f-84fa-46d9-a681-6ed966ae552f', '75386893-b93f-450a-83cb-433b120925ab', 14.1, 55, 5.99, true);
INSERT INTO public.weather_forecast VALUES ('38c54df5-aa20-48b7-8a7e-06b3681cf61d', '9f7d822e-c71d-4b44-bce7-4f7d4d1c80ee', 16.18, 61, 8.26, true);
INSERT INTO public.weather_forecast VALUES ('6359bc28-b44d-47d2-8b25-9daff0b3b7ec', '505635ab-cf65-42a8-9277-b0d9e7452cb0', 14.19, 53, 2.49, true);
INSERT INTO public.weather_forecast VALUES ('310f4e67-9c4a-4730-9220-ffe806e81f56', 'f8277e74-250d-424f-827a-1f8b3760049f', 1.73, 93, 5.83, true);
INSERT INTO public.weather_forecast VALUES ('e022a0ca-f66b-495b-b98c-946a8d893077', '5a46cac6-1f10-43ea-9d32-dc176fdaf32d', 5.1, 60, 2.72, true);
INSERT INTO public.weather_forecast VALUES ('1a8a94cc-5949-4b23-ba7e-0c01cf3d8657', 'adc8b8c9-8d7b-43ba-859f-fc47e04d36b9', 1.03, 63, 8.4, true);
INSERT INTO public.weather_forecast VALUES ('3cca0415-bdae-4859-b591-aaad50c184e2', '61aa159c-b9be-4fe9-b9f6-1f4fd1068533', 2.22, 30, 7.17, true);
INSERT INTO public.weather_forecast VALUES ('2b714faf-3731-4c2e-9704-f5a4e4c54e14', '460e808f-bda8-4d9a-8a00-c21dbf6d36b1', 6.06, 50, 5.36, true);
INSERT INTO public.weather_forecast VALUES ('e37617ad-9c4b-4903-9ecc-40e5e5f0d27e', 'd34a4042-7476-45bd-a0e1-5181934181b9', 11.11, 32, 2.25, true);
INSERT INTO public.weather_forecast VALUES ('e255512e-479e-479c-81ca-c29af09e9ce0', 'f1392254-a6ac-496b-8730-283416df9f8c', 13.07, 32, 2.71, true);


--
-- TOC entry 4852 (class 0 OID 32995)
-- Dependencies: 220
-- Data for Name: weather_settings; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.weather_settings VALUES ('a38c8b0b-dc08-4127-8d2e-48b5f64ad12b', 'e624a47cf606c5a8344e0f674cc9f255', 'Khmelnytskyi', '968b4c6b-ba60-4959-b1c5-28f8d11b35be');


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


-- Completed on 2025-03-21 17:28:15

--
-- PostgreSQL database dump complete
--

