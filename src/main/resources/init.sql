CREATE TABLE public.like (
	like_id BIGINT NOT NULL,
	id_user BIGINT NOT NULL,
	link_id BIGINT NOT NULL,
	PRIMARY KEY (like_id),
	CONSTRAINT user_pkey FOREIGN KEY (id_user) REFERENCES "user" ("id_user"),
	CONSTRAINT link_pkey FOREIGN KEY (link_id) REFERENCES "link" ("link_id")
);

CREATE SEQUENCE public.like_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	NO CYCLE;

ALTER TABLE public.link ADD COLUMN desktop_id bigint;
ALTER TABLE public.link ALTER COLUMN desktop_id SET NOT NULL ;
ALTER TABLE public.link ADD CONSTRAINT fk_desktop_id FOREIGN KEY (desktop_id) REFERENCES public.desktop(desktop_id);




// inicio

INSERT INTO type_link (type_link_id, deleted, insert_date, update_date, version, name) VALUES (nextval('public.typelinkid_seq'), false,now(),null, 0, 'URL');
INSERT INTO type_link (type_link_id, deleted, insert_date, update_date, version, name) VALUES (nextval('public.typelinkid_seq'), false,now(),null, 0, 'VIDEO');
INSERT INTO type_link (type_link_id, deleted, insert_date, update_date, version, name) VALUES (nextval('public.typelinkid_seq'), false,now(),null, 0, 'IMAGEN');
INSERT INTO type_link (type_link_id, deleted, insert_date, update_date, version, name) VALUES (nextval('public.typelinkid_seq'), false,now(),null, 0, 'MAPA');
INSERT INTO type_link (type_link_id, deleted, insert_date, update_date, version, name) VALUES (nextval('public.typelinkid_seq'), false,now(),null, 0, 'PRODUCTO');
INSERT INTO type_link (type_link_id, deleted, insert_date, update_date, version, name) VALUES (nextval('public.typelinkid_seq'), false,now(),null, 0, 'PUPOP');
INSERT INTO type_link (type_link_id, deleted, insert_date, update_date, version, name) VALUES (nextval('public.typelinkid_seq'), false,now(),null, 0, 'TEXTO');







#link category
INSERT INTO link_category (link_category_id, deleted, insert_date, update_date, version, name) VALUES (nextval('ytulink.sub_linkcategory_id_seq'), false, now(), null, 0, 'Internet');


#sub link category
INSERT INTO sub_link_category (sub_linkcategory_id, deleted, insert_date, update_date, version, name, link_category_id) VALUES (nextval('public.sub_linkcategory_seq'),false ,now(),null, 0,'prueba sub link ', (select link_category_id from link_category where name =  'prueba link'));


#link 
INSERT INTO link (link_id, deleted, insert_date, update_date, version, name, url_img, sub_link_category, type_link) VALUES  ( nextval('public.link_id_seq'),false,now(),null,0,'prueba link','',(select sub_linkcategory_id from sub_link_category where name = 'prueba sub link '),(select link_category_id from link_category where name =  'prueba link'));

#tipo de reaccion (like)
INSERT INTO type_reaction (type_reaction_id, deleted, insert_date, update_date, version, name) VALUES 
(nextval('public.type_reaction_seq'),false ,now(),null, 0, 'Like');





INSERT
INTO
    sub_link_category
    (
        sub_linkcategory_id,
        deleted,
        insert_date,
        update_date,
        version,
        name,
        link_category_id
    )
    VALUES
    (
        nextval('ytulink.sub_linkcategory_id_seq'),
        false ,
        now(),
        NULL,
        0,
        'Trailer',
        (
            SELECT
                link_category_id
            FROM
                link_category
            WHERE
                name = 'Videos')
    );




INSERT
INTO
    sub_link_category
    (
        sub_linkcategory_id,
        deleted,
        insert_date,
        update_date,
        version,
        name,
        link_category_id
    )
    VALUES
    (
        nextval('ytulink.sub_linkcategory_id_seq'),
        false ,
        now(),
        NULL,
        0,
        'Redes Sociales',
        (
            SELECT
                link_category_id
            FROM
                link_category
            WHERE
                name = 'Internet')
    );


