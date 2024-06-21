# Tugas 2 | Pembuatan API Subscription Sederhana
# Nama Anggota Kelompok
Nama    : I Nyoman Gede Candra Wikananta
NIM     : 2305551065

Nama    : Made Septino Budi Putrawan 
NIM     : 2305551083

## Daftar Isi

- [API Subscription](#tugas-2--pembuatan-api-subscription-sederhana)
    - [Pengenalan](#perkenalan)
    - [Struktur Program](#struktur-program)
    - [Prasyarat](#prasyarat)
    - [Batasan Program](#batasan-program)
    - [Penggunaan](#penggunaan)
        - [Clone Repo](#clone-repository)
        - [Install Maven](#install-maven)
        - [Run Main.java](#jalankan-mainjava)
    - [API-Key](#api-key)
    - [Spesifikasi API](#spesifikasi-api)
        - [GET CUSTOMER](#spesifikasi-api-get-customer)
        - [GET Subscriptions](#spesifikasi-api-get-subscriptions)
        - [GET Items](#spesifikasi-api-get-items)
        - [POST](#spesifikasi-api-post)
        - [PUT](#spesifikasi-api-put)
        - [DELETE](#spesifikasi-api-delete)
    - [Test pada program Postman](#test-program-dengan-postman)

## Perkenalan
Tugas 2 ini merupakan tugas yang mengharuskan mahasiswa untuk membuat sebuah backend API menggunakan bahasa pemrograman Java untuk sebuah sistem Subscrption sederhana. API ini nantinya akan digunakan untuk memanipulasi dan mengakses data pada sebuah database. API ini juga dapat meng-handle GET, POST, PUT, dan DELETE. Database nantinya akan disimpan pada software SQLite. Respon yang diberikan oleh server API dalam format JSON. Dan pengujian API dilakukan dengan software Postman.

## Struktur Program
API ini memiliki beberapa class yaitu class Controller, class Models, dan class Server. Masing - masing class memiliki fungsinya sendiri. Class untuk penempatan entitas ada pada class Models, class untuk keperluan database ada pada class Controller, dan untuk keperluan API ada pada class Server.

## Prasyarat
Adapun beberap prasyarat untuk menjalankan program ini yaitu:

- JDK versi 21
[Download JDK](https://www.oracle.com/id/java/technologies/downloads/)

- SQLite JDBC versi 3.23.1 or above
[Download SQLite](https://www.sqlite.org/download.html)

- Apache Maven versi 3.9.8
[Download Maven](https://maven.apache.org/download.cgi)

## Batasan Program
- Batasan dari program ini adalah hanya dapat menggunakan metode GET, POST, PUT dan DELETE.
- Tidak diperkenankan kesalahan dalam penulisan nama tabel, berikut daftar tabel dengan penulisan yang tepat:

```
Customers
Subscriptions
Items
Shipping_addresses
Cards
```

## Penggunaan
Berikut adalah langkah-langkah cara untuk menggunakan program backend API untuk sistem pembayaran subscription ini.

### Clone Repository
Lakukan _cloning_ pada repository ini dengan menggunakan _command_ git sebagai berikut:

```
$ git clone https://github.com/CandraWikananta/Tugas_2_PBO_Subscription
```

### Install Maven
Setelah melakukakan _cloning_, Anda harus memenuhi prasyarat pada bagian sebelumnya yaitu menginstall maven, karena program ini menggunakan Maven sebagau _build system_-nya, maka untuk melakukan instalasi gunakan _command_ berikut:

```
$ mvn install
```

### Jalankan Main.java
Jalankan `org.example.Main.main` untuk menggunakan program API ini pada:

```
localhost:9065
```

## API-Key
Program API ini juga menggunakan _API Key_ yang di _hardcoded_ pada kelas Main.java, untuk menggunakan program API ini pada POSTMAN harus menambahkan "API-Key" pada _Header_ dan _Value_ "API-Subscription-Key" untuk mendapatkan akses pada program API ini. Berikut adalah contoh memasukkan APi-Key.
![alt text](<IMG/Mendapatkan akses API-Key.png>)

## Spesifikasi API
Adapun juga beberapa spesifikasi API yang hanya dapat digunakan pada program ini yaitu sebagai berikut:

### Spesifikasi API GET Customer
- GET/customers => daftar semua pelanggan
- GET/customers/{id} => informasi pelanggan dan alamatnya
- GET/customers/{id}/cards => daftar kartu kredit/debit milik pelanggan
- GET/customers/{id}/subscriptions => daftar semua subscriptions milik
pelanggan
- GET/customers/{id}/subscriptions?subscriptions_status={active, cancelled,
non-renewing} => daftar semua subscriptions milik pelanggan yg berstatus
aktif / cancelled / non-renewing

### Spesifikasi API GET Subscriptions
- GET /subscriptions => daftar semua subscriptions
- GET /subscriptions?sort_by=current_term_end&sort_type=desc => daftar
semua subscriptions diurutkan berdasarkan current_term_end secara
descending
- GET /subscriptions/{id} =>
    - informasi subscription,
    - customer: id, first_name, last_name,
    - subscription_items: quantity, amount,
    - item: id, name, price, type

### Spesifikasi API GET Items
- GET /items => daftar semua produk
- GET /items?is_active=true => daftar semua produk yg memiliki status aktif
- GET /items/{id} => informasi produk

### Spesifikasi API POST
- POST /customers => buat pelanggan baru
- POST /subscriptions => buat subscription baru beserta dengan id customer,
shipping address, card, dan item yg dibeli
- POST /items => buat item baru

### Spesifikasi API PUT
- PUT /customers/{id}
- PUT /customers/{id}/shipping_addresses/{id}
- PUT /items/{id}

### Spesifikasi API DELETE
- DELETE /items/{id} => mengubah status item is_active menjadi false
- DELETE /customers/{id}/cards/{id} => menghapus informasi kartu kredit
pelanggan jika is_primary bernilai false

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