package code.storage.entity;

/** Common primary-key contract for every table in this single-domain application. */
public interface RegisterEntity {
  Long getIdRegister();

  void setIdRegister(Long idRegister);
}
