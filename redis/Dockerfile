FROM redis:latest
RUN mkdir /my-dir/
COPY my-data.redis /my-dir/
COPY start-redis.sh /my-dir/
CMD ["sh", "/my-dir/start-redis.sh"]
