//------items
create trigger wd_items_tg_ins after insert on wd_items 
 for each row insert into wd_data_sync(table_name,key_name,key_value,opt_type) 
values('wd_items ','id',new.id, 'New');

create trigger wd_items_tg_upd after update on wd_items 
 for each row insert into wd_data_sync(table_name,key_name,key_value,opt_type) 
values('wd_items','id', new.id, 'Update');

create trigger wd_items_tg_del before delete on wd_items  
 for each row insert into wd_data_sync(table_name,key_name,key_value,opt_type) 
values('wd_items','id', old.id, 'Delete');

//------ wd_shop
create trigger wd_shop_tg_ins after insert on wd_shop 
 for each row insert into wd_data_sync(table_name,key_name,key_value,opt_type) 
values('wd_shop ','id',new.id, 'New');

create trigger wd_shop_tg_upd after update on wd_shop 
 for each row insert into wd_data_sync(table_name,key_name,key_value,opt_type) 
values('wd_shop','id', new.id, 'Update');

create trigger wd_shop_tg_del before delete on wd_shop  
 for each row insert into wd_data_sync(table_name,key_name,key_value,opt_type) 
values('wd_shop','id', old.id, 'Delete');

//------ wd_seller
create trigger wd_seller_tg_ins after insert on wd_seller 
 for each row insert into wd_data_sync(table_name,key_name,key_value,opt_type) 
values('wd_seller ','id',new.id, 'New');

create trigger wd_seller_tg_upd after update on wd_seller 
 for each row insert into wd_data_sync(table_name,key_name,key_value,opt_type) 
values('wd_seller','id', new.id, 'Update');

create trigger wd_seller_tg_del before delete on wd_seller  
 for each row insert into wd_data_sync(table_name,key_name,key_value,opt_type) 
values('wd_seller','id', old.id, 'Delete');

