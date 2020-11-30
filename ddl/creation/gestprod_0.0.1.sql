CREATE TABLE users (
    id serial,
    username character varying(45) NOT NULL,
    name character varying(100) NOT NULL,
    surname character varying(100) NOT NULL,
    password character varying(160) NOT NULL,
    email character varying(100) NOT NULL,
    avatar bytea,
    style character varying(50),
    lang character varying(5),
    enabled integer DEFAULT 0 NOT NULL
);

ALTER TABLE users OWNER TO gestprod;

ALTER TABLE users ADD CONSTRAINT users_id_pkey PRIMARY KEY (id);
ALTER TABLE users ADD CONSTRAINT users_email_ukey UNIQUE (email);
ALTER TABLE users ADD CONSTRAINT users_username_ukey UNIQUE (username);

CREATE TABLE roles (
    id serial,
    name character varying(45) NOT NULL,
    description character varying(255)
);

ALTER TABLE roles OWNER TO gestprod;

ALTER TABLE roles ADD CONSTRAINT roles_id_pkey PRIMARY KEY (id);
ALTER TABLE roles ADD CONSTRAINT roles_name_ukey UNIQUE (name);

CREATE TABLE users_roles (
    role_id integer NOT NULL,
    user_id integer NOT NULL
);

ALTER TABLE users_roles OWNER TO gestprod;

ALTER TABLE users_roles ADD CONSTRAINT role_id__roles__fkey FOREIGN KEY (role_id) REFERENCES roles(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE users_roles ADD CONSTRAINT user_id__users__fkey FOREIGN KEY (user_id) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE;

CREATE TABLE customers (
    id serial,
    name character varying(200) NOT NULL
);

ALTER TABLE customers OWNER TO gestprod;

ALTER TABLE customers ADD CONSTRAINT customers_id_pkey PRIMARY KEY (id);

CREATE TABLE raw_materials (
    id serial,
    name character varying(200) NOT NULL
);


ALTER TABLE raw_materials OWNER TO gestprod;

ALTER TABLE raw_materials ADD CONSTRAINT raw_materials_id_pkey PRIMARY KEY (id);

CREATE TABLE additives (
    id serial,
    name character varying(200) NOT NULL
);

ALTER TABLE additives OWNER TO gestprod;

ALTER TABLE additives ADD CONSTRAINT additives_id_pkey PRIMARY KEY (id);

CREATE TABLE mixture_modes (
    id serial,
    name character varying(200) NOT NULL
);

ALTER TABLE mixture_modes OWNER TO gestprod;

ALTER TABLE mixture_modes ADD CONSTRAINT mixture_modes_id_pkey PRIMARY KEY (id);

CREATE TABLE packaging (
    id serial,
    packaging_mode character varying(200) NOT NULL
);


ALTER TABLE packaging OWNER TO gestprod;

ALTER TABLE packaging ADD CONSTRAINT packaging_id_pkey PRIMARY KEY (id);

CREATE TABLE production_orders (
    id serial,
    customer_id integer NOT NULL,
    production_order_code character varying(50) NOT NULL,
    production_number_lot character varying(50) NOT NULL,
    raw_material_id integer NOT NULL,
    weight_raw_material double precision NOT NULL,
    tons_raw_material integer NOT NULL,
    dry_residue double precision,
    density_raw_material double precision,
    expected_mixture_temperature double precision,
    expected_mixture_mode_id integer,
    expected_quantity_finished_product double precision,
    delivery_date date,
    packaging_id integer,
    production_order_date date,
    ddt_date date,
    ddt_number character varying(50)
);

ALTER TABLE production_orders OWNER TO gestprod;

ALTER TABLE production_orders ADD CONSTRAINT production_orders_id_pkey PRIMARY KEY (id);

ALTER TABLE production_orders ADD CONSTRAINT customer_id__customers__fkey FOREIGN KEY (customer_id) REFERENCES customers(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE production_orders ADD CONSTRAINT raw_material_id_raw_materials__fkey FOREIGN KEY (raw_material_id) REFERENCES raw_materials(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE production_orders ADD CONSTRAINT expected_mixture_mode_id__mixture_modes__fkey FOREIGN KEY (expected_mixture_mode_id) REFERENCES mixture_modes(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE production_orders ADD CONSTRAINT packaging_id__packaging__fkey FOREIGN KEY (packaging_id) REFERENCES packaging(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE TABLE additives_production_orders (
	id serial,
    additive_id integer NOT NULL,
    production_order_code_id integer NOT NULL,
    weight_additive double precision
);

ALTER TABLE additives_production_orders OWNER TO gestprod;

ALTER TABLE additives_production_orders ADD CONSTRAINT additives_production_orders_pkey PRIMARY KEY (id);

ALTER TABLE additives_production_orders ADD CONSTRAINT additive_id__additives_fkey FOREIGN KEY (additive_id) REFERENCES additives(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE additives_production_orders ADD CONSTRAINT production_order_code_id__production_orders__fkey FOREIGN KEY (production_order_code_id) REFERENCES production_orders(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE;

CREATE TABLE setting_phases (
    id serial,
    production_order_id integer NOT NULL,
    user_id integer NOT NULL,
    start_time timestamp with time zone,
    end_time timestamp with time zone,
    effective_mixture_temperature double precision,
    effective_mixture_mode_id integer
);

ALTER TABLE setting_phases OWNER TO gestprod;

ALTER TABLE setting_phases ADD CONSTRAINT setting_phases_id_pkey PRIMARY KEY (id);

ALTER TABLE setting_phases ADD CONSTRAINT production_order_id__production_orders__fkey FOREIGN KEY (production_order_id) REFERENCES production_orders(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE setting_phases ADD CONSTRAINT user_id__users_fkey FOREIGN KEY (user_id) REFERENCES users(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE setting_phases ADD CONSTRAINT effective_mixture_mode_id__mixture_modes__id FOREIGN KEY (effective_mixture_mode_id) REFERENCES mixture_modes(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE TABLE system_preparation_phases (
    id serial,
    production_order_id integer NOT NULL,
    user_id integer NOT NULL,
    start_time timestamp with time zone,
    end_time timestamp with time zone
);

ALTER TABLE system_preparation_phases OWNER TO gestprod;

ALTER TABLE system_preparation_phases ADD CONSTRAINT system_preparation_phases_id_pkey PRIMARY KEY (id);

ALTER TABLE system_preparation_phases ADD CONSTRAINT production_order_id__production_orders__fkey FOREIGN KEY (production_order_id) REFERENCES production_orders(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE system_preparation_phases ADD CONSTRAINT user_id__users_fkey FOREIGN KEY (user_id) REFERENCES users(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE TABLE working_phases (
    id serial,
    production_order_id integer NOT NULL
);

ALTER TABLE working_phases OWNER TO gestprod;

ALTER TABLE working_phases ADD CONSTRAINT working_phases_id_pkey PRIMARY KEY (id);

ALTER TABLE working_phases ADD CONSTRAINT production_order_id__production_orders__fkey FOREIGN KEY (production_order_id) REFERENCES production_orders(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE;

CREATE TABLE working_phases_users (
	id serial,
    working_phase_id integer NOT NULL,
    user_id integer NOT NULL,
    start_time timestamp with time zone,
    end_time timestamp with time zone
);

ALTER TABLE working_phases_users OWNER TO gestprod;

ALTER TABLE working_phases_users ADD CONSTRAINT working_phases_pkey PRIMARY KEY (id);

ALTER TABLE working_phases_users ADD CONSTRAINT working_phase_id__working_phases__fkey FOREIGN KEY (working_phase_id) REFERENCES working_phases(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE working_phases_users ADD CONSTRAINT user_id__users_fkey FOREIGN KEY (user_id) REFERENCES users(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE TABLE cleaning_phases (
    id serial,
    production_order_id integer NOT NULL,
    user_id integer NOT NULL,
    start_time timestamp with time zone,
    end_time timestamp with time zone
);

ALTER TABLE cleaning_phases OWNER TO gestprod;

ALTER TABLE cleaning_phases ADD CONSTRAINT cleaning_phases_id_pkey PRIMARY KEY (id);

ALTER TABLE cleaning_phases ADD CONSTRAINT production_order_id__production_orders__fkey FOREIGN KEY (production_order_id) REFERENCES production_orders(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE cleaning_phases ADD CONSTRAINT user_id__users_fkey FOREIGN KEY (user_id) REFERENCES users(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE TABLE validation_phases (
    id serial,
    production_order_id integer NOT NULL,
    user_id integer NOT NULL,
    start_time timestamp with time zone,
    end_time timestamp with time zone,
    humidity_finished_product double precision NOT NULL,
    density_finished_product double precision NOT NULL,
    packaging_state text,
    sieve_quantity double precision,
    chimney_quantity double precision,
    tower_entry_temperature double precision,
    tower_intern_temperature double precision,
    cyclon_entry_temperature double precision,
    note text
);

ALTER TABLE validation_phases OWNER TO gestprod;

ALTER TABLE validation_phases ADD CONSTRAINT validation_phases_id_pkey PRIMARY KEY (id);

ALTER TABLE validation_phases ADD CONSTRAINT production_order_id__production_orders__fkey FOREIGN KEY (production_order_id) REFERENCES production_orders(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE validation_phases ADD CONSTRAINT user_id__users_fkey FOREIGN KEY (user_id) REFERENCES users(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

--- additional table to store finished product quantity

CREATE TABLE working_phases_measurements (
	id serial,
	working_phase_id integer NOT NULL,
	user_id integer NOT NULL,
	time timestamp with time zone NOT NULL,
	finished_product_quantity double precision
);

ALTER TABLE working_phases_measurements OWNER TO gestprod;

ALTER TABLE working_phases_measurements ADD CONSTRAINT working_phases_measurements_id_pkey PRIMARY KEY (id);
ALTER TABLE working_phases_measurements ADD CONSTRAINT working_phase_id__working_phases__fkey FOREIGN KEY (working_phase_id) REFERENCES working_phases(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE;    
ALTER TABLE working_phases_measurements ADD CONSTRAINT user_id__users_fkey FOREIGN KEY (user_id) REFERENCES users(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
