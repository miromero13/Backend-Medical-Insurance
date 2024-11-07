package medical_insurance.backend_medical_insurance.common.enums;

public enum PermissionEnum {
  // User Permissions
  USER("Usuario"),
  USER_SHOW("Mostrar usuarios"),
  ROLE("Rol"),
  ROLE_SHOW("Mostrar roles"),
  PERMISSION("Permiso"),
  PERMISSION_SHOW("Mostrar permisos"),

  // Inventory Permissions
  PRODUCT("Producto"),
  PRODUCT_SHOW("Mostrar productos"),
  CATEGORY("Categoria"),
  CATEGORY_SHOW("Mostrar categorias"),
  GROUP("Grupo"),
  GROUP_SHOW("Mostrar grupos"),
  BATCH("Lote de producto"),
  BATCH_SHOW("Mostrar lotes de producto"),
  TANK("Tanque"),
  TANK_SHOW("Mostrar tanques"),
  FUEL("Combustible"),
  FUEL_SHOW("Mostrar combustibles"),
  PROVIDER("Proveedor"),
  PROVIDER_SHOW("Mostrar proveedores"),
  PRODUCT_OUTPUT("Salida de producto"),
  PRODUCT_OUTPUT_SHOW("Mostrar salidas de producto"),

  // Company Permissions
  COMPANY("Empresa"),
  BRANCH("Sucursal"),
  BRANCH_SHOW("Mostrar sucursales"),
  BINNACLE("Bitacora"),

  // Sale Permissions
  DISCOUNT("Descuento"),
  DISCOUNT_SHOW("Mostrar descuentos"),
  SALE_NOTE("Nota de venta"),
  SALE_NOTE_SHOW("Mostrar notas de venta"),
  DISPENSER("Dispensador"),
  DISPENSER_SHOW("Mostrar dispensadores"),
  HOSE("Mangueras"),
  HOSE_SHOW("Mostrar mangueras"),

  // Buy Permissions
  PURCHASE_ORDER("Orden de compra"),
  PURCHASE_ORDER_SHOW("Mostrar ordenes de compra"),
  BUY_NOTE("Nota de compra"),
  BUY_NOTE_SHOW("Mostrar notas de compra");

  public final String description;

  PermissionEnum(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
