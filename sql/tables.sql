-- Table for FeedComposition
CREATE TABLE feed_composition (
    id UUID  PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date VARCHAR(255) NOT NULL
);

-- Table for FeedComponent
CREATE TABLE feed_component (
    id UUID PRIMARY KEY,
    feed_composition_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    quantity VARCHAR(255) NOT NULL,
    FOREIGN KEY (feed_composition_id) REFERENCES feed_composition(id) ON DELETE CASCADE
);

-- Table for Vitamin
CREATE TABLE vitamin (
    id UUID PRIMARY KEY,
    feed_composition_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    quantity VARCHAR(255) NOT NULL,
    FOREIGN KEY (feed_composition_id) REFERENCES feed_composition(id) ON DELETE CASCADE
);

-- Table for statistic
CREATE TABLE public.day_statistic (
	amount int4 NULL,
	saved_at date NULL,
	id uuid NOT NULL,
	CONSTRAINT day_statistic_pkey PRIMARY KEY (id)
);

CREATE TABLE public.app_settings (
    id UUID PRIMARY KEY
);

CREATE TABLE public.weather_settings (
    id UUID PRIMARY KEY,
    weather_key VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    app_setting_id UUID NOT NULL,
    FOREIGN KEY (app_setting_id) REFERENCES settings(id) ON DELETE CASCADE
);

