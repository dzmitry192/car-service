/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.innowise.sivachenko.kafka.avro;
@org.apache.avro.specific.AvroGenerated
public enum AuditActionType implements org.apache.avro.generic.GenericEnumSymbol<AuditActionType> {
  GET, POST, PATCH, PUT, DELETE  ;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"enum\",\"name\":\"AuditActionType\",\"namespace\":\"com.innowise.sivachenko.kafka.avro\",\"symbols\":[\"GET\",\"POST\",\"PATCH\",\"PUT\",\"DELETE\"]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
}
