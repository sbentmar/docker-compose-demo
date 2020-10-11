wget https://github.com/aerokube/cm/releases/download/1.7.2/cm_linux_amd64
mv cm_linux_amd64 cm
chmod +x cm
./cm selenoid start --config-dir $(pwd)/config --tmpfs 128 --vnc
./cm selenoid-ui start
