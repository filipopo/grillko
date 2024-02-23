Example of Linux service to put into /etc/systemd/system/api.service
```
[Unit]
Description=api
After=network.target

[Service]
#User=www-data
WorkingDirectory=/var/www/html
ExecStart=node server.js
Restart=on-failure

[Install]
WantedBy=multi-user.target
```
The service can be enabled by running
```
systemctl daemon-reload
systemctl enable --now api
```
Apache2 virtual host example to put into /etc/apache2/sites-available/api.conf
```
<VirtualHost *:80>
        ServerName api.example.com

        ServerAdmin mail@example.com
        DocumentRoot /var/www/html

        <Directory /var/www/html/>
                Require all granted
        </Directory>

        Redirect /api /api/
        <Location /api/>
                ProxyPass "http://127.0.0.1:3000/api/"
                ProxyPassReverse "http://127.0.0.1:3000/api/"
        </Location>

        ErrorLog ${APACHE_LOG_DIR}/error.log
        CustomLog ${APACHE_LOG_DIR}/access.log combined
</VirtualHost>
```
The website can be enabled by running `a2ensite api`, a Let's encrypt SSL certificate can be added using `certbot -v --apache -n --email mail@example.com --agree-tos -d api.example.com`
