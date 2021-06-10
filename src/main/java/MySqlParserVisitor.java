// Generated from F:/sem6/dbsubmission/Database-Emulator33\MySqlParser.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MySqlParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MySqlParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MySqlParser#root}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoot(MySqlParser.RootContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#sqlStatements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSqlStatements(MySqlParser.SqlStatementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#sqlStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSqlStatement(MySqlParser.SqlStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#emptyStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyStatement(MySqlParser.EmptyStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#ddlStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDdlStatement(MySqlParser.DdlStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#dmlStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDmlStatement(MySqlParser.DmlStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#createIndex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateIndex(MySqlParser.CreateIndexContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#indexType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexType(MySqlParser.IndexTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#indexOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexOption(MySqlParser.IndexOptionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code copyCreateTable}
	 * labeled alternative in {@link MySqlParser#createTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCopyCreateTable(MySqlParser.CopyCreateTableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code queryCreateTable}
	 * labeled alternative in {@link MySqlParser#createTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryCreateTable(MySqlParser.QueryCreateTableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code columnCreateTable}
	 * labeled alternative in {@link MySqlParser#createTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnCreateTable(MySqlParser.ColumnCreateTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#ifNotExists}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfNotExists(MySqlParser.IfNotExistsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#createDefinitions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateDefinitions(MySqlParser.CreateDefinitionsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code columnDeclaration}
	 * labeled alternative in {@link MySqlParser#createDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnDeclaration(MySqlParser.ColumnDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code constraintDeclaration}
	 * labeled alternative in {@link MySqlParser#createDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintDeclaration(MySqlParser.ConstraintDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code indexDeclaration}
	 * labeled alternative in {@link MySqlParser#createDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexDeclaration(MySqlParser.IndexDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#columnDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnDefinition(MySqlParser.ColumnDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nullColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullColumnConstraint(MySqlParser.NullColumnConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code defaultColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultColumnConstraint(MySqlParser.DefaultColumnConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code autoIncrementColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAutoIncrementColumnConstraint(MySqlParser.AutoIncrementColumnConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primaryKeyColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryKeyColumnConstraint(MySqlParser.PrimaryKeyColumnConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code uniqueKeyColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUniqueKeyColumnConstraint(MySqlParser.UniqueKeyColumnConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code commentColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommentColumnConstraint(MySqlParser.CommentColumnConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code formatColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormatColumnConstraint(MySqlParser.FormatColumnConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code storageColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStorageColumnConstraint(MySqlParser.StorageColumnConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code referenceColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenceColumnConstraint(MySqlParser.ReferenceColumnConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code collateColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCollateColumnConstraint(MySqlParser.CollateColumnConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code generatedColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeneratedColumnConstraint(MySqlParser.GeneratedColumnConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code serialDefaultColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSerialDefaultColumnConstraint(MySqlParser.SerialDefaultColumnConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code checkColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCheckColumnConstraint(MySqlParser.CheckColumnConstraintContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#defaultValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultValue(MySqlParser.DefaultValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#currentTimestamp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCurrentTimestamp(MySqlParser.CurrentTimestampContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primaryKeyTableConstraint}
	 * labeled alternative in {@link MySqlParser#tableConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryKeyTableConstraint(MySqlParser.PrimaryKeyTableConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code uniqueKeyTableConstraint}
	 * labeled alternative in {@link MySqlParser#tableConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUniqueKeyTableConstraint(MySqlParser.UniqueKeyTableConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code foreignKeyTableConstraint}
	 * labeled alternative in {@link MySqlParser#tableConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForeignKeyTableConstraint(MySqlParser.ForeignKeyTableConstraintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code checkTableConstraint}
	 * labeled alternative in {@link MySqlParser#tableConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCheckTableConstraint(MySqlParser.CheckTableConstraintContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#referenceDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenceDefinition(MySqlParser.ReferenceDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#referenceAction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenceAction(MySqlParser.ReferenceActionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#referenceControlType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenceControlType(MySqlParser.ReferenceControlTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleIndexDeclaration}
	 * labeled alternative in {@link MySqlParser#indexColumnDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleIndexDeclaration(MySqlParser.SimpleIndexDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code specialIndexDeclaration}
	 * labeled alternative in {@link MySqlParser#indexColumnDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecialIndexDeclaration(MySqlParser.SpecialIndexDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionEngine}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionEngine(MySqlParser.TableOptionEngineContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionAutoIncrement}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionAutoIncrement(MySqlParser.TableOptionAutoIncrementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionAverage}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionAverage(MySqlParser.TableOptionAverageContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionCharset}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionCharset(MySqlParser.TableOptionCharsetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionChecksum}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionChecksum(MySqlParser.TableOptionChecksumContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionCollate}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionCollate(MySqlParser.TableOptionCollateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionComment}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionComment(MySqlParser.TableOptionCommentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionCompression}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionCompression(MySqlParser.TableOptionCompressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionConnection}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionConnection(MySqlParser.TableOptionConnectionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionDataDirectory}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionDataDirectory(MySqlParser.TableOptionDataDirectoryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionDelay}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionDelay(MySqlParser.TableOptionDelayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionEncryption}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionEncryption(MySqlParser.TableOptionEncryptionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionIndexDirectory}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionIndexDirectory(MySqlParser.TableOptionIndexDirectoryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionInsertMethod}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionInsertMethod(MySqlParser.TableOptionInsertMethodContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionKeyBlockSize}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionKeyBlockSize(MySqlParser.TableOptionKeyBlockSizeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionMaxRows}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionMaxRows(MySqlParser.TableOptionMaxRowsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionMinRows}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionMinRows(MySqlParser.TableOptionMinRowsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionPackKeys}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionPackKeys(MySqlParser.TableOptionPackKeysContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionPassword}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionPassword(MySqlParser.TableOptionPasswordContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionRowFormat}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionRowFormat(MySqlParser.TableOptionRowFormatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionRecalculation}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionRecalculation(MySqlParser.TableOptionRecalculationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionPersistent}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionPersistent(MySqlParser.TableOptionPersistentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionSamplePage}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionSamplePage(MySqlParser.TableOptionSamplePageContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionTablespace}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionTablespace(MySqlParser.TableOptionTablespaceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableOptionUnion}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableOptionUnion(MySqlParser.TableOptionUnionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#tablespaceStorage}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTablespaceStorage(MySqlParser.TablespaceStorageContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#partitionDefinitions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionDefinitions(MySqlParser.PartitionDefinitionsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionFunctionHash}
	 * labeled alternative in {@link MySqlParser#partitionFunctionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionFunctionHash(MySqlParser.PartitionFunctionHashContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionFunctionKey}
	 * labeled alternative in {@link MySqlParser#partitionFunctionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionFunctionKey(MySqlParser.PartitionFunctionKeyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionFunctionRange}
	 * labeled alternative in {@link MySqlParser#partitionFunctionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionFunctionRange(MySqlParser.PartitionFunctionRangeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionFunctionList}
	 * labeled alternative in {@link MySqlParser#partitionFunctionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionFunctionList(MySqlParser.PartitionFunctionListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subPartitionFunctionHash}
	 * labeled alternative in {@link MySqlParser#subpartitionFunctionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubPartitionFunctionHash(MySqlParser.SubPartitionFunctionHashContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subPartitionFunctionKey}
	 * labeled alternative in {@link MySqlParser#subpartitionFunctionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubPartitionFunctionKey(MySqlParser.SubPartitionFunctionKeyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionComparision}
	 * labeled alternative in {@link MySqlParser#partitionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionComparision(MySqlParser.PartitionComparisionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionListAtom}
	 * labeled alternative in {@link MySqlParser#partitionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionListAtom(MySqlParser.PartitionListAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionListVector}
	 * labeled alternative in {@link MySqlParser#partitionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionListVector(MySqlParser.PartitionListVectorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionSimple}
	 * labeled alternative in {@link MySqlParser#partitionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionSimple(MySqlParser.PartitionSimpleContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#partitionDefinerAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionDefinerAtom(MySqlParser.PartitionDefinerAtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#partitionDefinerVector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionDefinerVector(MySqlParser.PartitionDefinerVectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#subpartitionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubpartitionDefinition(MySqlParser.SubpartitionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionOptionEngine}
	 * labeled alternative in {@link MySqlParser#partitionOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionOptionEngine(MySqlParser.PartitionOptionEngineContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionOptionComment}
	 * labeled alternative in {@link MySqlParser#partitionOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionOptionComment(MySqlParser.PartitionOptionCommentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionOptionDataDirectory}
	 * labeled alternative in {@link MySqlParser#partitionOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionOptionDataDirectory(MySqlParser.PartitionOptionDataDirectoryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionOptionIndexDirectory}
	 * labeled alternative in {@link MySqlParser#partitionOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionOptionIndexDirectory(MySqlParser.PartitionOptionIndexDirectoryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionOptionMaxRows}
	 * labeled alternative in {@link MySqlParser#partitionOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionOptionMaxRows(MySqlParser.PartitionOptionMaxRowsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionOptionMinRows}
	 * labeled alternative in {@link MySqlParser#partitionOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionOptionMinRows(MySqlParser.PartitionOptionMinRowsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionOptionTablespace}
	 * labeled alternative in {@link MySqlParser#partitionOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionOptionTablespace(MySqlParser.PartitionOptionTablespaceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code partitionOptionNodeGroup}
	 * labeled alternative in {@link MySqlParser#partitionOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionOptionNodeGroup(MySqlParser.PartitionOptionNodeGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#intervalType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntervalType(MySqlParser.IntervalTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#deleteStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeleteStatement(MySqlParser.DeleteStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#insertStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertStatement(MySqlParser.InsertStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleSelect}
	 * labeled alternative in {@link MySqlParser#selectStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleSelect(MySqlParser.SimpleSelectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenthesisSelect}
	 * labeled alternative in {@link MySqlParser#selectStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesisSelect(MySqlParser.ParenthesisSelectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unionSelect}
	 * labeled alternative in {@link MySqlParser#selectStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnionSelect(MySqlParser.UnionSelectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unionParenthesisSelect}
	 * labeled alternative in {@link MySqlParser#selectStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnionParenthesisSelect(MySqlParser.UnionParenthesisSelectContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#updateStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdateStatement(MySqlParser.UpdateStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#insertStatementValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertStatementValue(MySqlParser.InsertStatementValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#updatedElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdatedElement(MySqlParser.UpdatedElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#assignmentField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentField(MySqlParser.AssignmentFieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#lockClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLockClause(MySqlParser.LockClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#singleDeleteStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleDeleteStatement(MySqlParser.SingleDeleteStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#multipleDeleteStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultipleDeleteStatement(MySqlParser.MultipleDeleteStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#singleUpdateStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleUpdateStatement(MySqlParser.SingleUpdateStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#multipleUpdateStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultipleUpdateStatement(MySqlParser.MultipleUpdateStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#orderByClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrderByClause(MySqlParser.OrderByClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#orderByExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrderByExpression(MySqlParser.OrderByExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#tableSources}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableSources(MySqlParser.TableSourcesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableSourceBase}
	 * labeled alternative in {@link MySqlParser#tableSource}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableSourceBase(MySqlParser.TableSourceBaseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableSourceNested}
	 * labeled alternative in {@link MySqlParser#tableSource}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableSourceNested(MySqlParser.TableSourceNestedContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atomTableItem}
	 * labeled alternative in {@link MySqlParser#tableSourceItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomTableItem(MySqlParser.AtomTableItemContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subqueryTableItem}
	 * labeled alternative in {@link MySqlParser#tableSourceItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubqueryTableItem(MySqlParser.SubqueryTableItemContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tableSourcesItem}
	 * labeled alternative in {@link MySqlParser#tableSourceItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableSourcesItem(MySqlParser.TableSourcesItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#indexHint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexHint(MySqlParser.IndexHintContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#indexHintType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexHintType(MySqlParser.IndexHintTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code innerJoin}
	 * labeled alternative in {@link MySqlParser#joinPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInnerJoin(MySqlParser.InnerJoinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code straightJoin}
	 * labeled alternative in {@link MySqlParser#joinPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStraightJoin(MySqlParser.StraightJoinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code outerJoin}
	 * labeled alternative in {@link MySqlParser#joinPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOuterJoin(MySqlParser.OuterJoinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code naturalJoin}
	 * labeled alternative in {@link MySqlParser#joinPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNaturalJoin(MySqlParser.NaturalJoinContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#queryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExpression(MySqlParser.QueryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#queryExpressionNointo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExpressionNointo(MySqlParser.QueryExpressionNointoContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#querySpecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuerySpecification(MySqlParser.QuerySpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#querySpecificationNointo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuerySpecificationNointo(MySqlParser.QuerySpecificationNointoContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#unionParenthesis}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnionParenthesis(MySqlParser.UnionParenthesisContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#unionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnionStatement(MySqlParser.UnionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#selectSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectSpec(MySqlParser.SelectSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#selectElements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectElements(MySqlParser.SelectElementsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selectStarElement}
	 * labeled alternative in {@link MySqlParser#selectElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectStarElement(MySqlParser.SelectStarElementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selectColumnElement}
	 * labeled alternative in {@link MySqlParser#selectElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectColumnElement(MySqlParser.SelectColumnElementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selectFunctionElement}
	 * labeled alternative in {@link MySqlParser#selectElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectFunctionElement(MySqlParser.SelectFunctionElementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selectExpressionElement}
	 * labeled alternative in {@link MySqlParser#selectElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectExpressionElement(MySqlParser.SelectExpressionElementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selectIntoVariables}
	 * labeled alternative in {@link MySqlParser#selectIntoExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectIntoVariables(MySqlParser.SelectIntoVariablesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selectIntoDumpFile}
	 * labeled alternative in {@link MySqlParser#selectIntoExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectIntoDumpFile(MySqlParser.SelectIntoDumpFileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selectIntoTextFile}
	 * labeled alternative in {@link MySqlParser#selectIntoExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectIntoTextFile(MySqlParser.SelectIntoTextFileContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#selectFieldsInto}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectFieldsInto(MySqlParser.SelectFieldsIntoContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#selectLinesInto}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectLinesInto(MySqlParser.SelectLinesIntoContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#fromClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFromClause(MySqlParser.FromClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#groupByClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupByClause(MySqlParser.GroupByClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#havingClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHavingClause(MySqlParser.HavingClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#groupByItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupByItem(MySqlParser.GroupByItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#limitClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLimitClause(MySqlParser.LimitClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#limitClauseAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLimitClauseAtom(MySqlParser.LimitClauseAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code describeStatements}
	 * labeled alternative in {@link MySqlParser#describeObjectClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDescribeStatements(MySqlParser.DescribeStatementsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code describeConnection}
	 * labeled alternative in {@link MySqlParser#describeObjectClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDescribeConnection(MySqlParser.DescribeConnectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#fullId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFullId(MySqlParser.FullIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#tableName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableName(MySqlParser.TableNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#fullColumnName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFullColumnName(MySqlParser.FullColumnNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#indexColumnName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexColumnName(MySqlParser.IndexColumnNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#mysqlVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMysqlVariable(MySqlParser.MysqlVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#charsetName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharsetName(MySqlParser.CharsetNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#collationName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCollationName(MySqlParser.CollationNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#engineName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEngineName(MySqlParser.EngineNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#uuidSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUuidSet(MySqlParser.UuidSetContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#xid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXid(MySqlParser.XidContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#xuidStringId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXuidStringId(MySqlParser.XuidStringIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#authPlugin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAuthPlugin(MySqlParser.AuthPluginContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#uid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUid(MySqlParser.UidContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#simpleId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleId(MySqlParser.SimpleIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#dottedId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDottedId(MySqlParser.DottedIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#decimalLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecimalLiteral(MySqlParser.DecimalLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#fileSizeLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFileSizeLiteral(MySqlParser.FileSizeLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#stringLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(MySqlParser.StringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#booleanLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(MySqlParser.BooleanLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#hexadecimalLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHexadecimalLiteral(MySqlParser.HexadecimalLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#nullNotnull}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullNotnull(MySqlParser.NullNotnullContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant(MySqlParser.ConstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringDataType(MySqlParser.StringDataTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nationalStringDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNationalStringDataType(MySqlParser.NationalStringDataTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nationalVaryingStringDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNationalVaryingStringDataType(MySqlParser.NationalVaryingStringDataTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dimensionDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDimensionDataType(MySqlParser.DimensionDataTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleDataType(MySqlParser.SimpleDataTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code collectionDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCollectionDataType(MySqlParser.CollectionDataTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code spatialDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpatialDataType(MySqlParser.SpatialDataTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code longVarcharDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLongVarcharDataType(MySqlParser.LongVarcharDataTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code longVarbinaryDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLongVarbinaryDataType(MySqlParser.LongVarbinaryDataTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#collectionOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCollectionOptions(MySqlParser.CollectionOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#convertedDataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConvertedDataType(MySqlParser.ConvertedDataTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#lengthOneDimension}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLengthOneDimension(MySqlParser.LengthOneDimensionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#lengthTwoDimension}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLengthTwoDimension(MySqlParser.LengthTwoDimensionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#lengthTwoOptionalDimension}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLengthTwoOptionalDimension(MySqlParser.LengthTwoOptionalDimensionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#uidList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUidList(MySqlParser.UidListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#tables}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTables(MySqlParser.TablesContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#indexColumnNames}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexColumnNames(MySqlParser.IndexColumnNamesContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#expressions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressions(MySqlParser.ExpressionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#expressionsWithDefaults}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionsWithDefaults(MySqlParser.ExpressionsWithDefaultsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#expressionOrDefault}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionOrDefault(MySqlParser.ExpressionOrDefaultContext ctx);
	/**
	 * Visit a parse tree produced by the {@code specificFunctionCall}
	 * labeled alternative in {@link MySqlParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecificFunctionCall(MySqlParser.SpecificFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code aggregateFunctionCall}
	 * labeled alternative in {@link MySqlParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAggregateFunctionCall(MySqlParser.AggregateFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code scalarFunctionCall}
	 * labeled alternative in {@link MySqlParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScalarFunctionCall(MySqlParser.ScalarFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code udfFunctionCall}
	 * labeled alternative in {@link MySqlParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUdfFunctionCall(MySqlParser.UdfFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code passwordFunctionCall}
	 * labeled alternative in {@link MySqlParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPasswordFunctionCall(MySqlParser.PasswordFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simpleFunctionCall}
	 * labeled alternative in {@link MySqlParser#specificFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleFunctionCall(MySqlParser.SimpleFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dataTypeFunctionCall}
	 * labeled alternative in {@link MySqlParser#specificFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataTypeFunctionCall(MySqlParser.DataTypeFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code valuesFunctionCall}
	 * labeled alternative in {@link MySqlParser#specificFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValuesFunctionCall(MySqlParser.ValuesFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code caseFunctionCall}
	 * labeled alternative in {@link MySqlParser#specificFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaseFunctionCall(MySqlParser.CaseFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code charFunctionCall}
	 * labeled alternative in {@link MySqlParser#specificFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharFunctionCall(MySqlParser.CharFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code positionFunctionCall}
	 * labeled alternative in {@link MySqlParser#specificFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPositionFunctionCall(MySqlParser.PositionFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code substrFunctionCall}
	 * labeled alternative in {@link MySqlParser#specificFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubstrFunctionCall(MySqlParser.SubstrFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code trimFunctionCall}
	 * labeled alternative in {@link MySqlParser#specificFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrimFunctionCall(MySqlParser.TrimFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code weightFunctionCall}
	 * labeled alternative in {@link MySqlParser#specificFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeightFunctionCall(MySqlParser.WeightFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code extractFunctionCall}
	 * labeled alternative in {@link MySqlParser#specificFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtractFunctionCall(MySqlParser.ExtractFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code getFormatFunctionCall}
	 * labeled alternative in {@link MySqlParser#specificFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGetFormatFunctionCall(MySqlParser.GetFormatFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#caseFuncAlternative}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaseFuncAlternative(MySqlParser.CaseFuncAlternativeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code levelWeightList}
	 * labeled alternative in {@link MySqlParser#levelsInWeightString}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLevelWeightList(MySqlParser.LevelWeightListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code levelWeightRange}
	 * labeled alternative in {@link MySqlParser#levelsInWeightString}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLevelWeightRange(MySqlParser.LevelWeightRangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#levelInWeightListElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLevelInWeightListElement(MySqlParser.LevelInWeightListElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#aggregateWindowedFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAggregateWindowedFunction(MySqlParser.AggregateWindowedFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#scalarFunctionName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScalarFunctionName(MySqlParser.ScalarFunctionNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#passwordFunctionClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPasswordFunctionClause(MySqlParser.PasswordFunctionClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#functionArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionArgs(MySqlParser.FunctionArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#functionArg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionArg(MySqlParser.FunctionArgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code isExpression}
	 * labeled alternative in {@link MySqlParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsExpression(MySqlParser.IsExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link MySqlParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpression(MySqlParser.NotExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicalExpression}
	 * labeled alternative in {@link MySqlParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalExpression(MySqlParser.LogicalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code predicateExpression}
	 * labeled alternative in {@link MySqlParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicateExpression(MySqlParser.PredicateExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code soundsLikePredicate}
	 * labeled alternative in {@link MySqlParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSoundsLikePredicate(MySqlParser.SoundsLikePredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionAtomPredicate}
	 * labeled alternative in {@link MySqlParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionAtomPredicate(MySqlParser.ExpressionAtomPredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jsonMemberOfPredicate}
	 * labeled alternative in {@link MySqlParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonMemberOfPredicate(MySqlParser.JsonMemberOfPredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inPredicate}
	 * labeled alternative in {@link MySqlParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInPredicate(MySqlParser.InPredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subqueryComparasionPredicate}
	 * labeled alternative in {@link MySqlParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubqueryComparasionPredicate(MySqlParser.SubqueryComparasionPredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code betweenPredicate}
	 * labeled alternative in {@link MySqlParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBetweenPredicate(MySqlParser.BetweenPredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryComparasionPredicate}
	 * labeled alternative in {@link MySqlParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryComparasionPredicate(MySqlParser.BinaryComparasionPredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code isNullPredicate}
	 * labeled alternative in {@link MySqlParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsNullPredicate(MySqlParser.IsNullPredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code likePredicate}
	 * labeled alternative in {@link MySqlParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLikePredicate(MySqlParser.LikePredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code regexpPredicate}
	 * labeled alternative in {@link MySqlParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegexpPredicate(MySqlParser.RegexpPredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryExpressionAtom}
	 * labeled alternative in {@link MySqlParser#expressionAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpressionAtom(MySqlParser.UnaryExpressionAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code collateExpressionAtom}
	 * labeled alternative in {@link MySqlParser#expressionAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCollateExpressionAtom(MySqlParser.CollateExpressionAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mysqlVariableExpressionAtom}
	 * labeled alternative in {@link MySqlParser#expressionAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMysqlVariableExpressionAtom(MySqlParser.MysqlVariableExpressionAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nestedExpressionAtom}
	 * labeled alternative in {@link MySqlParser#expressionAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNestedExpressionAtom(MySqlParser.NestedExpressionAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nestedRowExpressionAtom}
	 * labeled alternative in {@link MySqlParser#expressionAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNestedRowExpressionAtom(MySqlParser.NestedRowExpressionAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mathExpressionAtom}
	 * labeled alternative in {@link MySqlParser#expressionAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMathExpressionAtom(MySqlParser.MathExpressionAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code existsExpressionAtom}
	 * labeled alternative in {@link MySqlParser#expressionAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExistsExpressionAtom(MySqlParser.ExistsExpressionAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intervalExpressionAtom}
	 * labeled alternative in {@link MySqlParser#expressionAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntervalExpressionAtom(MySqlParser.IntervalExpressionAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jsonExpressionAtom}
	 * labeled alternative in {@link MySqlParser#expressionAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonExpressionAtom(MySqlParser.JsonExpressionAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subqueryExpressionAtom}
	 * labeled alternative in {@link MySqlParser#expressionAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubqueryExpressionAtom(MySqlParser.SubqueryExpressionAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code constantExpressionAtom}
	 * labeled alternative in {@link MySqlParser#expressionAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantExpressionAtom(MySqlParser.ConstantExpressionAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code functionCallExpressionAtom}
	 * labeled alternative in {@link MySqlParser#expressionAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCallExpressionAtom(MySqlParser.FunctionCallExpressionAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryExpressionAtom}
	 * labeled alternative in {@link MySqlParser#expressionAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpressionAtom(MySqlParser.BinaryExpressionAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fullColumnNameExpressionAtom}
	 * labeled alternative in {@link MySqlParser#expressionAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFullColumnNameExpressionAtom(MySqlParser.FullColumnNameExpressionAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bitExpressionAtom}
	 * labeled alternative in {@link MySqlParser#expressionAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitExpressionAtom(MySqlParser.BitExpressionAtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#unaryOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOperator(MySqlParser.UnaryOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#comparisonOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonOperator(MySqlParser.ComparisonOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#logicalOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOperator(MySqlParser.LogicalOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#bitOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitOperator(MySqlParser.BitOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#mathOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMathOperator(MySqlParser.MathOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#jsonOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonOperator(MySqlParser.JsonOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#charsetNameBase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharsetNameBase(MySqlParser.CharsetNameBaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#transactionLevelBase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransactionLevelBase(MySqlParser.TransactionLevelBaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#privilegesBase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrivilegesBase(MySqlParser.PrivilegesBaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#intervalTypeBase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntervalTypeBase(MySqlParser.IntervalTypeBaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#dataTypeBase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataTypeBase(MySqlParser.DataTypeBaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#keywordsCanBeId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeywordsCanBeId(MySqlParser.KeywordsCanBeIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MySqlParser#functionNameBase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionNameBase(MySqlParser.FunctionNameBaseContext ctx);
}