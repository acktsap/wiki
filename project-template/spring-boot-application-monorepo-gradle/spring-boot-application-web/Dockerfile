FROM centos:centos7

# set user
RUN useradd -ms /bin/bash god
USER god

# change working directory
ARG WORKDIR=/app
WORKDIR ${WORKDIR}

# install graalvm
ARG JAVA_VERSION=java17
ARG ARCH=amd64
ARG GRAALVM_VERSION=21.3.3
RUN curl -L https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-${GRAALVM_VERSION}/graalvm-ce-${JAVA_VERSION}-linux-${ARCH}-${GRAALVM_VERSION}.tar.gz -o graalvm.tar.gz && \
    mkdir -p graalvm && \
    tar -zxf graalvm.tar.gz --strip-components=1 -C graalvm && \
    rm graalvm.tar.gz
ENV PATH=/app/graalvm/bin:$PATH

# copy distribution
COPY ./build/distributions .
RUN tar --strip-components=1 -zxf spring-boot-application-web.tar.gz && \
    rm spring-boot-application-web.tar.gz
EXPOSE 8080/tcp

# set entrypoint
ENTRYPOINT ["./entrypoint.sh"]
