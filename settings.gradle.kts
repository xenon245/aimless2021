rootProject.name = "aimless"

val prefix = "aimless"
val core = "$prefix-core"

include(
    "$prefix-api",
    "$prefix-core",
    "$prefix-debug"
)

file(core).listFiles()?.filter {
    it.isDirectory && it.name.startsWith("v")
}?.forEach { file ->
    include(":$core:${file.name}")
}