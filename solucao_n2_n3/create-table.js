var params = {
    TableName: 'TopSecretReceivedMessages',
    KeySchema: [
        { AttributeName: 'satellite', KeyType: 'HASH' }
    ],
    AttributeDefinitions: [
        {  AttributeName: 'satellite', AttributeType: 'S', }
    ],
    ProvisionedThroughput: { ReadCapacityUnits: 1, WriteCapacityUnits: 1 }
};

dynamodb.createTable(params, function(err, data) {
    if (err) ppJson(err);
    else ppJson(data);
});