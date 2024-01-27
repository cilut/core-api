package com.cilut.coreapi.request;

import java.io.Serializable;

public record LocationRequest(Double latitude,
                              Double longitude) implements Serializable {
}
