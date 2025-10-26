-- Migration for reporter requests and news status
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='ReporterRequests' AND xtype='U')
CREATE TABLE ReporterRequests (
  Id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
  Fullname NVARCHAR(200),
  Email NVARCHAR(200),
  RequestedAt DATETIME DEFAULT GETDATE()
);

IF NOT EXISTS (SELECT * FROM syscolumns WHERE id=OBJECT_ID('News') AND name='Status')
BEGIN
  ALTER TABLE News ADD Status NVARCHAR(50) DEFAULT 'approved';
END;