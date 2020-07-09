FROM oracle/graalvm-ce:20.1.0-java11 as graalvm
RUN gu install native-image

COPY . /home/app/micronaut_gvm
WORKDIR /home/app/micronaut_gvm

RUN native-image --no-server -cp target/micronaut_gvm-*.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/micronaut_gvm/micronaut_gvm /app/micronaut_gvm
ENTRYPOINT ["/app/micronaut_gvm"]
