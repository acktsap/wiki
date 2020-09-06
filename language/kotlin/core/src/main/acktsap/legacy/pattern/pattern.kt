package legacy.pattern

// 점증적 생성자 패턴
// pros : No unmanaged state
// cons : Too many constructor
// kotlin : ?

// 빈즈 패턴
// pros : just add setter, getter on more field
// cons : Unmanaged state
// kotlin : resolve cons with `Person().apply { this.content = "test" }`

// 빌더 패턴 : 점증적 생성자 패턴 + 빈즈 패턴
// pros : No unmanaged state
// cons : Too many constructor
// kotlin : no builder pattern

