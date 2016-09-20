# PHP Custom Query Builder

This is a custom made simple PHP Query Builder that provides
easy access to functions and make queries easy to understand and execute.

## Requirements

1. PHP 5.5 or greater

_Note: for 5.4 or lower support use mysqli instead

## Installation

Download the foldes **core**, **classes**, **functions**, **helpers** into your webserver public_html directory. To test run the file test.php located in the directory.
Make the appropriate configuration settings in the **core/init.php** file so that it can connect to the database and make sure that you set the name of the **public_html** 
folder in the config file in both places like this:

```php
'folder' => array(
        'root' => 'root_folder_name',

'base_url' => $protocol.'localhost/root_folder_name/',
```

## Handling The Query Builder

The **core/init.php** must be required from every file that you create. The classes are autoloaded.

The DB class is using the Singleton Pattern to avoid multiple unnecessary connections to the database. So you can use the instance very easily like:

```php
$db = DB::getInstance();
$data = $db->get('users');

//Will Generate: SELECT * FROM users
```
You can also use the builder with custom fields and tables like this:

```php

$data = $db->select('user_id, user_email')->from('users')->fetch();
//Will Generate: SELECT user_id, user_email FROM users

$data = $db->select('user_id', 'user_email')->from('users')->fetch();
//Will Generate: SELECT user_id, user_email FROM users
```
All queries are using prepared statements and binding values. You can use where, and, or statements like this:


```php
$data = $db->select('*')->from('users')->where('id', '=', '3')->fetch();
//Will Generate: SELECT * FROM users WHERE id = 3 and the values will be binded

$data = $db->select('*')->from('users')->where('id', '=', '3')->and('email', '=', 'myemail@example.com')->fetch();
//Will Generate: SELECT * FROM users WHERE id = 3 AND email = 'myemail@example.com' and the values will be binded
```

It also supports multiple AND or OR statements like this:

```php
$data = $db->select('*')
           ->from('users')
           ->where('id', '=', '3')
           ->and('email', '=', 'myemail@example.com')
           ->and('date', '>', '2016-01-01')
           ->fetch();
//Will Generate: SELECT * FROM users WHERE id = ? AND email = ? AND date > ? and the values will be binded

$data = $db->select('*')
           ->from('users')
           ->where('id', '=', '3')
           ->and('email', '=', 'myemail@example.com')
           ->and('date', '>', '2016-01-01')
           ->or('date', '<', '2016-08-01')
           ->fetch();
//Will Generate: SELECT * FROM users WHERE id = 3 AND email = 'myemail@example.com' AND date > '2016-01-01' OR date < '2016-08-01' and the values will be binded
```

It also supports BETWEEN, NOT BETWEEN, LIKE, NOT LIKE, IN, NOT IN, REGEXP, NOT REGEXP, IFNULL, IS NULL, IS NOT NULL, HAVING, GROUP BY, ORDER BY, LIMIT statements like this:

```php
$data = $db->select('*')
           ->from('users')
           ->where('id', '=', '3')
           ->between('date', '2016-01-01', '2016-08-01')
           ->fetch();
//Will Generate: SELECT * FROM users WHERE id = 3 AND date BETWEEN '2016-01-01' AND '2016-08-01' and the values will be binded

$data = $db->select('*')
           ->from('users')
           ->where('id', '=', '3')
           ->like('email', 'myemail@example.com')
           ->fetch();
//Will Generate: SELECT * FROM users WHERE id = 3 AND email LIKE '%myemail@example.com%'

$data = $db->select('*')
           ->from('users')
           ->where('id', '=', '3')
           ->in('postal_code', '000, 111, 222, 333')
           ->fetch();
//Will Generate: SELECT * FROM users WHERE id = 3 AND postal_code IN('000, 111, 222, 333') and the values will be binded

$data = $db->select('*')
           ->from('users')
           ->where('id', '=', '3')
           ->regex('postal_code', '000|111|222|333')
           ->fetch();
//Will Generate: SELECT * FROM users WHERE id = 3 AND postal_code REGEXP '000|111|222|333' and the values will be binded

$data = $db->select('*')
           ->from('users')
           ->where('id', '=', '3')
           ->if_null('postal_code', '0')
           ->fetch();
//Will Generate: SELECT * FROM users WHERE id = 3 AND IFNULL(NULL, postal_code = 0) and the values will be binded

$data = $db->select('*')
           ->from('users')
           ->where('id', '=', '3')
           ->null('address')
           ->fetch();
//Will Generate: SELECT * FROM users WHERE id = 3 AND address IS NULL and the values will be binded

$data = $db->select('*')
           ->from('users')
           ->where('id', '=', '3')
           ->having('address', '=', 'My Street')
           ->fetch();
//Will Generate: SELECT * FROM users WHERE id = 3 HAVING address = 'My Street' and the values will be binded

$data = $db->select('*')
           ->from('users')
           ->where('id', '=', '3')
           ->having('address', '=', 'My Street')
           ->group('user_id')
           ->fetch();
//Will Generate: SELECT * FROM users WHERE id = 3 HAVING address = 'My Street' GROUP BY user_id and the values will be binded

$data = $db->select('*')
           ->from('users')
           ->where('id', '=', '3')
           ->having('address', '=', 'My Street')
           ->group('user_id')
           ->order('email', 'ASC')
           ->fetch();
//Will Generate: SELECT * FROM users WHERE id = 3 HAVING address = 'My Street' GROUP BY user_id ORDER BY email ASC and the values will be binded

$data = $db->select('*')
           ->from('users')
           ->where('id', '=', '3')
           ->having('address', '=', 'My Street')
           ->group('user_id')
           ->order('email', 'ASC')
           ->limit(5)
           ->fetch();
//Will Generate: SELECT * FROM users WHERE id = 3 HAVING address = 'My Street' GROUP BY user_id ORDER BY email ASC LIMIT 5,0 and the values will be binded


```

The builder also supports JOINS like this:


```php
$data = $db->select('*')
           ->from('users')
           ->join('orders', 'users.user_id = orders.user', 'INNER')
           ->where('user_id', '=', '3')
           ->order('user_id', 'ASC')
           ->fetch();
//Will Generate: SELECT * FROM users INNER JOIN orders ON users.user_id = orders.user WHERE user_id = 3 ORDER BY user_id ASC and the values will be binded

$data = $db->select('*')
           ->from('users')
           ->join('orders', 'users.user_id = orders.user', 'INNER')
           ->join('reviews', 'orders.review_id = reviews.order', 'INNER')
           ->where('user_id', '=', '3')
           ->order('user_id', 'ASC')
           ->fetch();
//Will Generate: SELECT * FROM users INNER JOIN orders ON users.user_id = orders.user INNER JOIN reviews ON orders.review_id = reviews.order WHERE user_id = 3 ORDER BY user_id ASC and the values will be binded
```


## Responses

The returning values from the tables are fetched as Objects and you can print_pre() them like this:

```php
print_pre($data->results());

//or if you only want the 1st object

print_pre($data->first());
```


## Pull Requests

Pull Requests and suggestions are very welcomed!!!