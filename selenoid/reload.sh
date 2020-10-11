sudo rm config/video/*
sudo rm config/logs/*
./cm selenoid stop
./cm selenoid stop
./cm selenoid start --config-dir $(pwd)/config --tmpfs 128 --vnc
./cm selenoid-ui start
