# Tugas 2 | Pembuatan API Subscription Sederhana
# Nama Anggota Kelompok
Nama    : I Nyoman Gede Candra Wikananta
NIM     : 2305551065

Nama    : Made Septino Budi Putrawan 
NIM     : 2305551083

## Perkenalan
Tugas 2 ini merupakan tugas yang mengharuskan mahasiswa untuk membuat sebuah backend API menggunakan bahasa pemrograman Java untuk sebuah sistem Subscrption sederhana. API ini nantinya akan digunakan untuk memanipulasi dan mengakses data pada sebuah database. API ini juga dapat meng-handle GET, POST, PUT, dan DELETE. Database nantinya akan disimpan pada software SQLite. Respon yang diberikan oleh server API dalam format JSON. Dan pengujian API dilakukan dengan software Postman.

## Struktur Program
API ini memiliki beberapa class yaitu class Controller, class Models, dan class Server. Masing - masing class memiliki fungsinya sendiri. Class untuk penempatan entitas ada pada class Models, class untuk keperluan database ada pada class Controller, dan untuk keperluan API ada pada class Server.

## Test Program dengan POSTMAN

### GET
GET/Customer
</br>
`http://localhost:9065/Customer`
</br>
Untuk mendapatkan daftar semua pelanggan atau customer. 
</br>
![alt text](<IMG/Screenshot (123).png>)

GET/Customer/{id}
</br>
`http://localhost:9065/Customer/1`
</br>
Untuk mendapatkan informasi lengkap milik salah satu pelanggan menggunakan id.
</br>
![alt text](<IMG/Screenshot (125).png>)

GET/Customer/{id}/Cards
</br>
`http://localhost:9065/Customer/1/Cards`
</br>
Untuk mendapatkan informasi daftar kartu kredit salah satu pelanggan menggunakan id.
</br>
![alt text](<IMG/Screenshot (127).png>)

GET/Customer/{id}/Subscriptions
</br>
`http://localhost:9065/Customer/1/Subscriptions`
</br>
Untuk mendapatkan daftar langganan atau subscription milik salah satu pelanggan menggunakan id.
</br>
![alt text](<IMG/Screenshot (128).png>)

GET/Customer/{id}/Subscriptions?Subscriptions_status=active
</br>
`http://localhost:9065/Customer/1/Subscriptions?Subscriptions_status=active`
</br>
Untuk mendapatkan daftar semua subscription milik pelanggan yang berstatus tertentu (active/cancelled).
</br>
![alt text](<IMG/Screenshot (129).png>)

GET/Subscriptions
</br>
`http://localhost:9065/Subscriptions`
</br>
Untuk mendapatkan daftar semua langganan atau subscriptions.
</br>
![alt text](<IMG/Screenshot (130).png>)

GET/Subscriptions?sort_by=current_term_end&sort_type=desc
</br>
`http://localhost:9065/Customer/1/Subscriptions`
</br>
Untuk mendapatkan daftar semua subscriptions atau langganan yang diurutkan berdasarkan current_term_end.
</br>
![alt text](<IMG/Screenshot (131).png>)

GET/Subscriptions/{id}
</br>
`http://localhost:9065/Subscriptions/1`
</br>
Untuk mendapatkan daftar langganan atau subscriptions berdasarkan salah satu id.
</br>
![alt text](<IMG/Screenshot (132).png>)

GET/Items
</br>
`http://localhost:9065/Items`
</br>
Untuk mendapatkan daftar semua items atau barang.
</br>
![alt text](<IMG/Screenshot (133).png>)

GET/Items?Is_active=true
</br>
`http://localhost:9065/Items?Is_active=true`
</br>
Untuk mendapatkan daftar semua barang atau produk yang masih meiliki status aktif.
</br>
![alt text](<IMG/Screenshot (134).png>)

GET/Items/{id}
</br>
`http://localhost:9065/Items/1`
</br>
Untuk mendapatkan informasi mengenai suatu items atau produk berdasarkan id.
</br>
![alt text](<IMG/Screenshot (135).png>)

### POST
POST/Customers
</br>
`http://localhost:9065/Customer`
</br>
Untuk membuat daftar pelanggan atau customer baru.
</br>
![alt text](<IMG/Screenshot (136).png>)

POST/Subscriptions
</br>
`http://localhost:9065/Items`
</br>
Untuk menambahkan daftar langganan atau subscriptions baru.
</br>
![alt text](<IMG/Screenshot (137).png>)

POST/Items
</br>
`http://localhost:9065/Subscriptions`
</br>
Untuk menambahkan daftar items atau barang baru.
</br>
![alt text](<IMG/Screenshot (138).png>)

### PUT
PUT/Customers/{id}
</br>
`http://localhost:9065/Customer/1`
</br>
Untuk mempebarui data pelanggan atau customer yang sudah ada.
</br>
![alt text](<IMG/Screenshot (139).png>)

PUT/Customers/{id}/Shipping_addresses/{id}
</br>
`http://localhost:9065/Customer/1/Shipping_addresses/1`
</br>
Untuk memperbarui data shipping addresses atau alamat pengiriman seorang pelanggan atau customer yang sudah ada.
</br>
![alt text](<IMG/Screenshot (140).png>)

PUT/Items/{id}
</br>
`http://localhost:9065/Items/1`
</br>
Untuk memperbarui data item atau produk yang sudah ada.
</br>
![alt text](<IMG/Screenshot (141).png>)


### DELETE
DELETE/Items/{id}
</br>
`http://localhost:9065/Items/6`
</br>
Untuk menghapus data item atau produk yang sudah ada berdasarkan id.
</br>
![alt text](<IMG/Screenshot (142).png>)

DELETE/Customers/{id}/Cards/{id}
</br>
`http://localhost:9065/Customer/1/Cards/2`
</br>
Untuk menghapus data kartu pelanggan atau customer yang sudah ada berdasarkan id.
</br>
![alt text](<IMG/Screenshot (143).png>)

### Error 404 dan 400
404 
</br>
`http://localhost:9065/Customer/13`
</br>
Data customer dengan Id 13(Datanya tidak tersedia pada database).
</br>
![alt text](<IMG/Screenshot (144).png>)

400
</br>
`http://localhost:9065/Customer/13:12`
</br>
(Format Id yang tidak sesuai).
</br>
![alt text](<IMG/Screenshot (145).png>)