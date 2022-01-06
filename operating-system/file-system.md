# File System

- [File System Interface](#file-system-interface)
  - [File](#file)
    - [File Attributes](#file-attributes)
    - [File Operations](#file-operations)
  - [Access Method](#access-method)
    - [Sequential Access](#sequential-access)
    - [Direct Access](#direct-access)
    - [Other Access Methods](#other-access-methods)
  - [Directory](#directory)
  - [Directory Structure](#directory-structure)
  - [Mounting](#mounting)
  - [File Sharing](#file-sharing)
  - [File Protection](#file-protection)
- [File-System Implementation](#file-system-implementation)
  - [Structure](#structure)
  - [Implementation](#implementation)
  - [Directory Implementation](#directory-implementation)
  - [Allocation Methods](#allocation-methods)
  - [Free-Space Management](#free-space-management)
  - [Efficiency and Performance](#efficiency-and-performance)
  - [Recovery](#recovery)
- [Reference](#reference)

## File System Interface

### File

- Storage에 대한 추상화를 파일이라고 함.
- 파일에 접근하기 위해서는 파일을 open하고 처리 후 close해야 함.
- File lock도 제공.

#### File Attributes

- Name
- Identifier
- Type
- Location
- Size
- Protection
- Time & Date
- User ID

#### File Operations

- Creating
- Writing
- Reading
- Deleting
- Repositioning : 해당 file에서 데이터 찾는거. I/O 안일어남.
- Truncating : 파일 삭제 안하고 파일 contents 만 다 지우는거.

### Access Method

#### Sequential Access

- magnetic tape 과 비슷한 operation을 제공.
  - read next
  - write next
  - rewind
  - skip n records

#### Direct Access

- record로 jump해서 읽을 수 있는 operation을 제공.
  - read n
  - write n
  - jump to record n

#### Other Access Methods

- Direct access 기반으로 index 같은거를 할 수도 있음.

### Directory

todo

### Directory Structure

### Mounting

### File Sharing

### File Protection

## File-System Implementation

### Structure

### Implementation

### Directory Implementation

### Allocation Methods

### Free-Space Management

### Efficiency and Performance

### Recovery

## Reference

- Operating System Concepts (Operating System Concepts, Ninth Edition)
  - [File-System Interface](https://www.cs.uic.edu/~jbell/CourseNotes/OperatingSystems/11_FileSystemInterface.html)
  - [File-System Implementation](https://www.cs.uic.edu/~jbell/CourseNotes/OperatingSystems/12_FileSystemImplementation.html)